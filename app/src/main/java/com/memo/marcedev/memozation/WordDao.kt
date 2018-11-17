package com.memo.marcedev.memozation

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Insert()
    fun insert(word: Word)

    @Update()
    fun update(word: Word)

    @Delete
    fun delete(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()
}