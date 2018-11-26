package com.memo.marcedev.memozation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.word_layout.view.*

class WordsAdapter(val onWordSelected: OnWordSelected) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private val words = mutableListOf<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_layout, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount() = words.size


    override fun onBindViewHolder(viewHolder: WordViewHolder, position: Int) {
        viewHolder.bind(words[position])
    }

    fun updateWords(words: List<Word>){
        this.words.clear()
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    fun allWordAsString(): String = words.map {it.word}.joinToString(", ")

    inner class WordViewHolder(item: View): RecyclerView.ViewHolder(item){

        private val wordTextView= item.text_view_word
        private val editButton = item.button_edit
        private val deleteButton = item.button_delete

        fun bind(word: Word){
            wordTextView.text = word.word
            editButton.setOnClickListener { onWordSelected.edit(word) }
            deleteButton.setOnClickListener { onWordSelected.delete(word) }
        }
    }
}