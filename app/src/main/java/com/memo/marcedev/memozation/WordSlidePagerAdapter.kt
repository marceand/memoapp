package com.memo.marcedev.memozation

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WordSlidePagerAdapter : PagerAdapter() {

    private var words = mutableListOf<Word>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.word_layout, container, false)
        val wordTextView: TextView = layout.findViewById(R.id.text_view_word) as TextView
        wordTextView.text = words[position].word
        container.addView(layout)
        return layout
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return words.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    fun addWords(givenWords: List<Word>){
        words.clear()
        words.addAll(givenWords)
        notifyDataSetChanged()
    }
}