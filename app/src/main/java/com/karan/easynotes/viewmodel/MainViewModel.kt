package com.karan.easynotes.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.karan.easynotes.model.Note
import com.karan.easynotes.repo.NotesRepository

class MainViewModel : ViewModel(){

    fun insert(context: Context, note: Note)
    {
        NotesRepository.insert(note,context)
    }

    fun getAllNotesCards(context: Context): LiveData<List<Note>>?
    {
        return NotesRepository.getAllNotes(context)
    }

    fun update(context: Context, note:Note)
    {
        NotesRepository.update(note,context)
    }

    fun search(context: Context, data:String): LiveData<List<Note>>?
    {
        return NotesRepository.search(data , context)
    }

    fun delete(context: Context, note: Note)
    {
        NotesRepository.delete(note,context)
    }

}