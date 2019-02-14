package com.marceme.mvocabulary

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


class MainActivity : AppCompatActivity(), OnVocabularySelected {

    private lateinit var vocabularyViewModel: VocabularyViewModel
    private lateinit var vocabularyAdapter: VocabularyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        vocabularyViewModel = ViewModelProviders.of(this).get(VocabularyViewModel::class.java)

        vocabularyAdapter = VocabularyAdapter(this)
        val viewManager = LinearLayoutManager(this)

        recyclerview_vocabulary.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = vocabularyAdapter
        }


        vocabularyViewModel.allVocabulary.observe(this, Observer { vocabularyWords ->
            vocabularyWords?.let { vocabularyAdapter.updateVocabularyWords(it) }
        })

        fab.setOnClickListener { addVocabulary() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vocabulary, menu)
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
        emailIntent.putExtra(Intent.EXTRA_TEXT, vocabularyAdapter.allWordAsString())
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    private fun addVocabulary() {
        val dialogView = View.inflate(this, R.layout.new_vocabulary, null)
        val editText = dialogView.findViewById(R.id.edit_vocabulary) as EditText
        val editDialog = AlertDialog.Builder(this)
                .setTitle(R.string.add_vocabulary_title)
                .setView(dialogView)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save){ _, _ -> saveVocabulary(editText)}
                .create()
        editDialog.show()
    }

    private fun saveVocabulary(editText: EditText){
        val word = editText.text.toString()
        vocabularyViewModel.save(word)
    }

    override fun edit(vocabulary: Vocabulary) {
        onVocabularyEdited(vocabulary)
    }

    private fun onVocabularyEdited(vocabulary: Vocabulary) {
        val dialogView = View.inflate(this, R.layout.new_vocabulary, null)
        val editText = dialogView.findViewById(R.id.edit_vocabulary) as EditText
        editText.setText(vocabulary.word)

        val editDialog = AlertDialog.Builder(this)
                .setTitle(R.string.edit_title)
                .setView(dialogView)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save){ _, _ -> saveVocabulary(editText, vocabulary)}
                .create()
        editDialog.show()
    }

    override fun delete(vocabulary: Vocabulary) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.delete_title)
        builder.setMessage(R.string.delete_message)
        builder.setNegativeButton(R.string.action_cancel, null)
        builder.setPositiveButton(R.string.action_delete) { _, _ -> deleteVocabulary(vocabulary)}
        builder.create().show()
    }

    private fun deleteVocabulary(vocabulary: Vocabulary) {
        vocabularyViewModel.delete(vocabulary)
    }

    private fun saveVocabulary(editText: EditText, vocabulary: Vocabulary) {
        val wordEdited = editText.text.toString()
        vocabularyViewModel.update(wordEdited, vocabulary)
    }

}
