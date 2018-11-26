package com.memo.marcedev.memozation

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(var word: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}