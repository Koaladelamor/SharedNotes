package com.example.sharednotes.main

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sharednotes.R
import com.example.sharednotes.main.Note
import com.example.sharednotes.databinding.ItemNoteBinding

class MyNotesRecyclerViewAdapter(
    var myNotes: ArrayList<Note>,
    val context: Context
) : RecyclerView.Adapter<MyNotesRecyclerViewAdapter.NoteVH>() {

    inner class NoteVH(binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.NoteTitle
        val description = binding.NoteDescription
        val recipient = binding.NoteRecipient
        val reminder = binding.NoteReminder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return NoteVH(binding)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        val note = myNotes[position]
        holder.title.text = note.title
        holder.description.text = note.description
        holder.recipient.text = note.recipient
        holder.reminder.text = note.reminder
        holder.title.setOnLongClickListener {
            showRenameDialog(note, position)
            true
        }


    }

    private fun showRenameDialog(note: Note, position: Int) {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Particle name")

        val editText = EditText(context)
        editText.setText(note.title)
        builder.setView(editText)

        builder.setPositiveButton("Ok") { _, _ ->
            note.title = editText.text.toString()
            notifyItemChanged(position)
        }
        builder.setPositiveButton("Cancel") { _, _ ->
            Toast.makeText(context, "S'ha apretat Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }


    fun updateNotesList(notes: ArrayList<Note>) {
        this.myNotes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return myNotes.size
    }
}