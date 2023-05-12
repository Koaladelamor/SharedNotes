package com.example.sharednotes.main

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sharednotes.databinding.ItemNoteRequestBinding


class RequestsRecyclerViewAdapter(
    var myRequests: ArrayList<Note>,
    val context: Context,
    val notesVM: NotesViewModel
) : RecyclerView.Adapter<RequestsRecyclerViewAdapter.NoteVH>() {

    //private val notesViewModel: NotesViewModel by viewModels()

    inner class NoteVH(binding: ItemNoteRequestBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.NoteTitle
        val description = binding.NoteDescription
        val recipient = binding.NoteRecipient
        //val reminder = binding.NoteReminder
        //val status = binding.NoteRequestStatus
        val acceptRequest = binding.AcceptRequestButton
        val cancelRequest = binding.CancelRequestButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteRequestBinding.inflate(layoutInflater, parent, false)
        return NoteVH(binding)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        val request = myRequests[position]
        holder.title.text = request.title
        holder.description.text = request.description
        holder.recipient.text = request.recipient
        //holder.reminder.text = note.reminder
        //holder.status.text = holder.status.text.toString().plus(note.status)
        holder.title.setOnLongClickListener {
            showRenameDialog(request, position)
            true
        }

        holder.acceptRequest.setOnClickListener {
            val recipient = request.recipient.replace(".", "")
            notesVM.acceptRequest(position, recipient)
        }

        holder.cancelRequest.setOnClickListener {
            val recipient = request.recipient.replace(".", "")
            notesVM.rejectRequest(position, recipient)
        }
    }

    private fun showRenameDialog(note: Note, position: Int) {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Note title")

        val editText = EditText(context)
        editText.setText(note.title)
        builder.setView(editText)

        builder.setPositiveButton("Ok") { _, _ ->
            note.title = editText.text.toString()
            notifyItemChanged(position)
            // TODO: actualizar en bd
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(context, "S'ha apretat Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }


    fun updateNotesList(notes: ArrayList<Note>) {
        this.myRequests = notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return myRequests.size
    }
}