package com.karan.easynotes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.karan.easynotes.model.Note
import com.karan.easynotes.ui.MainActivity
import com.karan.easynotes.viewmodel.MainViewModel
import java.util.*
import kotlin.properties.Delegates

private lateinit var getData : String
private lateinit var viewModel : MainViewModel
private lateinit var date : Date
private var getCharacter by Delegates.notNull<Long>()
private lateinit var getDate : String

private lateinit var eNote : AppCompatEditText
private lateinit var tvCurrentDate : AppCompatTextView
private lateinit var tvCharacters : AppCompatTextView

private val textObserver = object : TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


    @SuppressLint("SetTextI18n")
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        tvCharacters.text = " | Characters ${p0?.length.toString()}"
    }

    override fun afterTextChanged(p0: Editable?) {}

}

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        init()
        viewModel = MainViewModel()
        getDate()

        eNote.addTextChangedListener(textObserver)
    }

    private fun init(){
        eNote = findViewById(R.id.note)
        tvCurrentDate = findViewById(R.id.currentDate)
        tvCharacters = findViewById(R.id.characters)
    }

    private fun backToHome(){
        startActivity(Intent(this , MainActivity::class.java))
        finish()
    }

    private fun getDate(){
        date = Calendar.getInstance().time
        tvCurrentDate.text = date.toString()
    }

    private fun saveInDB(){
        getData = eNote.text.toString()
        getDate = tvCurrentDate.text.toString()
        getCharacter = getData.trim().length.toLong()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu , menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save -> {
                saveInDB()
                if(getData.isNotEmpty()){
                    viewModel.insert(applicationContext , Note(getData , getDate , getCharacter))
                    backToHome()
                }else{
                    Toast.makeText(applicationContext , "Empty Note" , Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}