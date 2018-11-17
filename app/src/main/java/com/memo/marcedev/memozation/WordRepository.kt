package com.memo.marcedev.memozation

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    @WorkerThread
    suspend fun update(word: Word) {
        wordDao.update(word)
    }

    @WorkerThread
    suspend fun delete(word: Word) {
        wordDao.delete(word);
    }
}