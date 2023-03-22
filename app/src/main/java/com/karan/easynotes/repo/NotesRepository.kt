package com.karan.easynotes.repo

import android.content.Context
import com.karan.easynotes.model.Note
import androidx.lifecycle.LiveData
import com.karan.easynotes.database.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepository {
    /// no need to create object of repository
    companion object{
        private lateinit var db: NoteDB

        private fun getDBInstance(context : Context): NoteDB{
            return NoteDB.getInstance(context)
        }

        fun getAllNotes(context : Context) : LiveData<List<Note>>{
            db = getDBInstance(context)
            return db.getDao().getAllNotes()
        }
        fun search(data : String , context : Context): LiveData<List<Note>> {
            db = getDBInstance(context)
            return db.getDao().search(data)
        }

        /// suspended
        fun insert(note : Note , context : Context){
            db = getDBInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().insert(note)
            }
        }

        fun update(note : Note , context : Context){
            db = getDBInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().update(note)
            }
        }

        fun delete(note : Note , context : Context){
            db = getDBInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().delete(note)
            }
        }
    }
}