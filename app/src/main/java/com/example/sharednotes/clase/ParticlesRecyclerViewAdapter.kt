package com.example.sharednotes.clase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sharednotes.R
import com.example.sharednotes.databinding.ItemParticleBinding

class ParticlesRecyclerViewAdapter(
    var particles: ArrayList<Particle>,
    val context: Context
) : RecyclerView.Adapter<ParticlesRecyclerViewAdapter.ParticleVH>()
{

    inner class ParticleVH(binding: ItemParticleBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.particleName
        val image = binding.particleImage
        val card = binding.particleCardView
    }

    override fun onCreateViewHolder(  parent: ViewGroup, viewType: Int): ParticleVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemParticleBinding.inflate(layoutInflater)
        return ParticleVH(binding)
    }

    override fun onBindViewHolder(holder: ParticleVH, position: Int) {
        val particle = particles[position]
        holder.name.text = particle.name
        holder.card.setOnLongClickListener{
            showRenameDialog(particle, position)
            true
        }

        when(particle.family){
            Particle.Family.QUARK -> R.color.quarks
            Particle.Family.LEPTON -> R.color.leptons
            Particle.Family.GAUGE_BOSON -> R.color.gauge_bosons
            Particle.Family.SCALAR_BOSON -> R.color.higgs
        }
    }

    private fun showRenameDialog(particle: Particle, position: Int) {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Particle name")

        val editText = EditText(context)
        editText.setText(particle.name)
        builder.setView(editText)

        builder.setPositiveButton("Ok"){ _, _ ->
            particle.name = editText.text.toString()
            notifyItemChanged(position)
        }
        builder.setPositiveButton("Cancel"){ _, _ ->
            Toast.makeText(context, "S'ha apretat Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    override fun getItemCount(): Int {
        return particles.size
    }

    fun updateParticlesList(particles: ArrayList<Particle>){
        this.particles = particles
        notifyDataSetChanged()
    }
}