package com.example.sharednotes.clase


import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.sharednotes.databinding.ActivityImageCaptureBinding

class ImageCaptureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageCaptureBinding

    private val activityResultTakePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
// Aqui definim què fer un cop tenim el resultat
            if(RESULT != null){
                binding.imagePreview.setImageBitmap(RESULT)
            }

        }

    private val activityRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
// Aqui definim què fer un cop tenim el resultat
            if(isGranted) {
                activityResultTakePictureLauncher.launch(null)
            }
            else{
                Toast.makeText(this, "denegau", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.takePictureButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Ja tinc el permís
                activityResultTakePictureLauncher.launch(null)
            } else {
                // No tinc el permís i l'he de sol·licitar
                activityRequestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}