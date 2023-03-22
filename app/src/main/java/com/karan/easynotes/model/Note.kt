package com.karan.easynotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


const val TABLE_NAME = "notes"

@Entity(tableName = TABLE_NAME)
data class Note(
    var data:String,
    var date:String,
    var characters:Long ) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null;
}
