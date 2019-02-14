package com.marceme.mvocabulary

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface VocabularyDao {

    @Query("SELECT * from vocabulary_table ORDER BY word ASC")
    fun getAllVocabulary(): LiveData<List<Vocabulary>>

    @Insert()
    fun insert(vocabulary: Vocabulary)

    @Update()
    fun update(vocabulary: Vocabulary)

    @Delete
    fun delete(vocabulary: Vocabulary)

    @Query("DELETE FROM vocabulary_table")
    fun deleteAll()
}