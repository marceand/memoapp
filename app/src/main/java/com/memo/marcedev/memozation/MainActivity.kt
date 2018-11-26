package com.memo.marcedev.memozation

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.content_main.*
import android.content.Intent
import android.net.Uri


class MainActivity : AppCompatActivity(), OnWordSelected{

    private lateinit var wordViewModel: WordViewModel
    private lateinit var wordAdapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        wordAdapter = WordsAdapter(this)
        val viewManager = LinearLayoutManager(this)

        recyclerview_words.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = wordAdapter
        }


        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { wordAdapter.updateWords(it) }
        })

        fab.setOnClickListener { addNewWord() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_word, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_share -> {
                showShareDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showShareDialog() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Vocabulary words")
        emailIntent.putExtra(Intent.EXTRA_TEXT, wordAdapter.allWordAsString())
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    private fun addNewWord() {
        val dialogView = View.inflate(this, R.layout.activity_new_word, null)
        val editText = dialogView.findViewById(R.id.edit_word) as EditText
        val editDialog = AlertDialog.Builder(this)
                .setTitle(R.string.add_word_title)
                .setView(dialogView)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save){_,_ -> saveWord(editText)}
                .create()
        editDialog.show()
    }

    private fun saveWord(editText: EditText){
        val word = editText.text.toString()
        wordViewModel.save(word)
    }

    override fun edit(word: Word) {
        onWordEdited(word)
    }

    private fun onWordEdited(word: Word) {
        val dialogView = View.inflate(this, R.layout.activity_new_word, null)
        val editText = dialogView.findViewById(R.id.edit_word) as EditText
        editText.setText(word.word)

        val editDialog = AlertDialog.Builder(this)
                .setTitle(R.string.edit_title)
                .setView(dialogView)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save){_,_ -> saveWord(editText, word)}
                .create()
        editDialog.show()
    }

    override fun delete(word: Word) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.delete_title)
        builder.setMessage(R.string.delete_message)
        builder.setNegativeButton(R.string.action_cancel, null)
        builder.setPositiveButton(R.string.action_delete) { _, _ -> deleteWord(word)}
        builder.create().show()
    }

    private fun deleteWord(word: Word) {
        wordViewModel.delete(word)
    }

    private fun saveWord(editText: EditText, word: Word) {
        val wordEdited = editText.text.toString()
        wordViewModel.update(wordEdited, word)
    }

}
