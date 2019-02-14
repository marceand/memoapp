package com.marceme.mvocabulary

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vocabulary_row_layout.view.*

class VocabularyAdapter(val onVocabularySelected: OnVocabularySelected) : RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>() {

    private val vocabularyWords = mutableListOf<Vocabulary>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): VocabularyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vocabulary_row_layout, parent, false)
        return VocabularyViewHolder(view)
    }

    override fun getItemCount() = vocabularyWords.size


    override fun onBindViewHolder(viewHolder: VocabularyViewHolder, position: Int) {
        viewHolder.bind(vocabularyWords[position])
    }

    fun updateVocabularyWords(vocabularies: List<Vocabulary>){
        this.vocabularyWords.clear()
        this.vocabularyWords.addAll(vocabularies)
        notifyDataSetChanged()
    }

    fun allWordAsString(): String = vocabularyWords.map {it.word}.joinToString(", ")

    inner class VocabularyViewHolder(item: View): RecyclerView.ViewHolder(item){

        private val wordTextView= item.text_view_word
        private val editButton = item.button_edit
        private val deleteButton = item.button_delete

        fun bind(vocabulary: Vocabulary){
            wordTextView.text = vocabulary.word
            editButton.setOnClickListener { onVocabularySelected.edit(vocabulary) }
            deleteButton.setOnClickListener { onVocabularySelected.delete(vocabulary) }
        }
    }
}