package com.memo.marcedev.memozation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import java.lang.Exception
import kotlin.coroutines.experimental.CoroutineContext

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    fun save(word: String){
        if(word.isNotEmpty()) {
            val newWord = Word(word)
            insert(newWord)
        }
    }

    private fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        try {
            repository.insert(word)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(word: Word) = scope.launch(Dispatchers.IO) {
        try {
            repository.delete(word)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun update(wordEdited: String, word: Word) {
        if (wordEdited.isNotEmpty()) {
            word.word= wordEdited
            update(word)
        }
    }

    private fun update(word: Word) = scope.launch(Dispatchers.IO) {
        try {
            repository.update(word)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
