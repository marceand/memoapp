package com.marceme.mvocabulary

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import java.lang.Exception
import kotlin.coroutines.experimental.CoroutineContext

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: VocabularyRepository
    val allVocabulary: LiveData<List<Vocabulary>>

    init {
        val vocabularyDao = VocabularyRoomDatabase.getDatabase(application).vocabularyDao()
        repository = VocabularyRepository(vocabularyDao)
        allVocabulary = repository.allVocabulary
    }

    fun save(word: String){
        if(word.isNotEmpty()) {
            val vocabulary= Vocabulary(word)
            insert(vocabulary)
        }
    }

    private fun insert(vocabulary: Vocabulary) = scope.launch(Dispatchers.IO) {
        try {
            repository.insert(vocabulary)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(vocabulary: Vocabulary) = scope.launch(Dispatchers.IO) {
        try {
            repository.delete(vocabulary)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun update(wordEdited: String, vocabulary: Vocabulary) {
        if (wordEdited.isNotEmpty()) {
            vocabulary.word= wordEdited
            update(vocabulary)
        }
    }

    private fun update(vocabulary: Vocabulary) = scope.launch(Dispatchers.IO) {
        try {
            repository.update(vocabulary)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
