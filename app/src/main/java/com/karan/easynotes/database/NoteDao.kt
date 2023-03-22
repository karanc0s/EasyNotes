package com.karan.easynotes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.karan.easynotes.model.Note
import com.karan.easynotes.model.TABLE_NAME

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note : Note)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM $TABLE_NAME WHERE data LIKE :data1")
    fun search(data1 : String) : LiveData<List<Note>>

    @Delete
    suspend fun delete(note: Note)
}