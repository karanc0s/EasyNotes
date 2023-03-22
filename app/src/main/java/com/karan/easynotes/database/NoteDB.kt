package com.karan.easynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karan.easynotes.model.Note

@Database(entities = [Note::class] , version = 1 )
abstract class NoteDB : RoomDatabase(){

    abstract fun getDao() : NoteDao

    companion object{
        private const val DATABASE_NAME ="NoteDB"

        var sInstance : NoteDB? = null

        @Synchronized
        fun getInstance(context : Context) : NoteDB{
            if(sInstance == null){
                sInstance = Room.databaseBuilder(
                    context.applicationContext ,
                    NoteDB::class.java ,
                    DATABASE_NAME
                ).build()
            }
            return sInstance!!
        }
    }

}