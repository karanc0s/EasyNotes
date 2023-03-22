package com.karan.easynotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.karan.easynotes.R
import com.karan.easynotes.model.Note
import com.karan.easynotes.viewmodel.MainViewModel
import java.util.Calendar
import java.util.Date

class UpdateActivity : AppCompatActivity() {

    private lateinit var date : Date
    private lateinit var getNote : Note
    private lateinit var viewModel : MainViewModel

    private val textObserver = object:TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun afterTextChanged(p0: Editable?) {}
    }

    private lateinit var tvDate : AppCompatTextView
    private lateinit var tvCurrChars : AppCompatTextView
    private lateinit var etUpdateNote : AppCompatEditText
    private lateinit var fabUpdate : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        init()
        getDate()
        viewModel = MainViewModel()

        tvCurrChars.text = "char 0"
        etUpdateNote.addTextChangedListener(textObserver)

        val bundle = intent.getSerializableExtra("note" , Note::class.java)

        if(bundle!=null){
            getNote = bundle
        }
        loadNote(getNote)

        fabUpdate.setOnClickListener{
            updateNote()
        }



    }

    private fun init(){
        tvDate = findViewById(R.id.tv_time)
        tvCurrChars = findViewById(R.id.tv_update_chars)
        etUpdateNote = findViewById(R.id.et_update_note)
        fabUpdate = findViewById(R.id.fab_update)
    }

    private fun loadNote(note : Note){
        etUpdateNote.setText(note.data)
        tvCurrChars.text = "${note.characters}"
    }

    private fun updateNote(){
        getNote.data = etUpdateNote.text.toString()
        getNote.date = tvDate.text.toString()
        getNote.characters = etUpdateNote.text.toString().length.toLong()

        viewModel.update(applicationContext , getNote)
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
    }

    private fun getDate(){
        date = Calendar.getInstance().time
        tvDate.text = date.toString()
    }


}