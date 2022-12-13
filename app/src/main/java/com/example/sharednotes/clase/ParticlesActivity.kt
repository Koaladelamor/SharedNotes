package com.example.sharednotes.clase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharednotes.databinding.ActivityParticlesBinding
import com.google.firebase.firestore.FirebaseFirestore

class ParticlesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticlesBinding
    private lateinit var firestore: FirebaseFirestore

    private var particles = arrayListOf<Particle>()

    private val adapter = ParticlesRecyclerViewAdapter(particles, this)

    companion object{
        const val COLLECTION_PARTICLES = "particles"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val collection = firestore.collection(COLLECTION_PARTICLES)
        collection.get().addOnSuccessListener {
            particles = it.documents.mapNotNull { dbParticle ->
                dbParticle.toObject(Particle::class.java)
            } as ArrayList
        }.addOnFailureListener{
            Toast.makeText(this, "The request has failed", Toast.LENGTH_SHORT).show()
        }
        adapter.updateParticlesList(particles)
        binding.particlesRecyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()

        val collection = firestore.collection(COLLECTION_PARTICLES)
        for (particle in particles){
            //write
            collection.document(particle.name).set(particle)
        }
    }


}