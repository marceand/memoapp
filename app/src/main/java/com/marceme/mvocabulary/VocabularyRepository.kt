package com.marceme.mvocabulary

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class VocabularyRepository(private val vocabularyDao: VocabularyDao) {

    val allVocabulary: LiveData<List<Vocabulary>> = vocabularyDao.getAllVocabulary()

    @WorkerThread
    suspend fun insert(vocabulary: Vocabulary) {
        vocabularyDao.insert(vocabulary)
    }

    @WorkerThread
    suspend fun update(vocabulary: Vocabulary) {
        vocabularyDao.update(vocabulary)
    }

    @WorkerThread
    suspend fun delete(vocabulary: Vocabulary) {
        vocabularyDao.delete(vocabulary);
    }
}