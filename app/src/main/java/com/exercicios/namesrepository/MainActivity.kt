package com.exercicios.namesrepository

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var names: MutableList<String> = mutableListOf()

    private lateinit var rvNames: RecyclerView
    private lateinit var fabAddName: FloatingActionButton
    private lateinit var etName: EditText
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var nameAdapter: NameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeApp()
    }

    private fun initializeApp() {
        findViewsById()
        createNameAdapter()
        createComponentInteractions()
    }

    private fun findViewsById() {
        this.rvNames = findViewById(R.id.rvNames)
        this.fabAddName = findViewById(R.id.fabAddName)
    }

    private fun createNameAdapter() {
        this.nameAdapter = NameAdapter(this.names)
        nameAdapter.onItemClickRecyclerView = OnItemClick()
        this.rvNames.adapter = nameAdapter
    }

    private fun createComponentInteractions() {
        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvNames)
        this.textToSpeech = TextToSpeech(this, null)
        this.fabAddName.setOnClickListener{add()}
    }

    private fun add() {
        this.etName = EditText(this)
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Novo Nome!")
            setMessage("Digite o novo nome")
            setView(this@MainActivity.etName)
            setPositiveButton("Save", OnClick())
            setNegativeButton("Cancel", null)
        }
        builder.create().show()
    }

    inner class OnClick : DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            val name = this@MainActivity.etName.text.toString()
            (this@MainActivity.rvNames.adapter as NameAdapter).add(name)
        }

    }

    inner class OnItemClick : OnItemClickRecyclerView {
        override fun onItemClick(position: Int) {
            val name = this@MainActivity.names.get(position)
            this@MainActivity.textToSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    inner class OnSwipe : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            (this@MainActivity.rvNames.adapter as NameAdapter).move(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (this@MainActivity.rvNames.adapter as NameAdapter).del(viewHolder.adapterPosition)
        }

    }
}