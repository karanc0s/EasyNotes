package com.karan.easynotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.karan.easynotes.AddActivity
import com.karan.easynotes.R
import com.karan.easynotes.adapter.NoteAdapter
import com.karan.easynotes.eventhandler.EventHandler
import com.karan.easynotes.model.Note
import com.karan.easynotes.viewmodel.MainViewModel

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() , EventHandler {

    private lateinit var splashScreen: SplashScreen
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteViewModel: MainViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteList: ArrayList<Note>
    private lateinit var searchView: SearchView
    private lateinit var fab : FloatingActionButton

    private val touchCallback = object : ItemTouchHelper.SimpleCallback( 0 , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val note = noteList[position]

            when(direction){
                ItemTouchHelper.RIGHT -> {
                    noteViewModel.delete(this@MainActivity, note)
                }
                ItemTouchHelper.LEFT -> {
                    noteViewModel.delete(this@MainActivity , note)
                }
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreen = installSplashScreen()
        keepSplashScreenFor5Seconds()

        setContentView(R.layout.activity_main)

        initialize()
        initRecyclerView()
//        setSupportActionBar(findViewById(R.id.toolbar))

        noteViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        noteViewModel.getAllNotesCards(this)?.observe(this) {
            noteAdapter.setData(it as ArrayList<Note>)
            noteList = it
        }

        fab.setOnClickListener{
            val intent = Intent(this , AddActivity::class.java)
            startActivity(intent)
        }

        val itemTouchEvent = ItemTouchHelper(touchCallback)
        itemTouchEvent.attachToRecyclerView(recyclerView)

    }
    private fun initialize(){
        recyclerView = findViewById(R.id.note_rv)
        fab = findViewById(R.id.fab)
        noteAdapter = NoteAdapter(applicationContext , ArrayList<Note>() , this)
    }
    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2 , LinearLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun onClickEventHandler(position: Int) {
        val intent = Intent(this , UpdateActivity::class.java)
        intent.putExtra("note" , noteList[position])
        startActivity(intent)
    }
    private fun getItemFromDB(data : String){
        var searchedData = data
        searchedData = "%$data%"

        noteViewModel.search(this , searchedData)?.observe(this) {
            Log.d(TAG, "getItemFromDB: $it ${it.size}")
            noteAdapter.setData(it as ArrayList<Note>)
        }


    }

    ///// For Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu , menu)
        val search : MenuItem? = menu!!.findItem(R.id.searchItems)
        searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!= null){
                    Log.d(TAG, "onQueryTextSubmit: $query")
                    getItemFromDB(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!= null){
                    Log.d(TAG, "onQueryTextChange: $newText")
                    getItemFromDB(newText)
                }
                return true
            }

        })
        
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting -> {}
            R.id.list -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun keepSplashScreenFor5Seconds() {
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                Thread.sleep(1500)
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

}