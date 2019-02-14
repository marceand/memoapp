package com.marceme.mvocabulary

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "vocabulary_table")
class Vocabulary(var word: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}