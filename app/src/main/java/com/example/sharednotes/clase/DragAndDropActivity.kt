package com.example.sharednotes.clase

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import com.example.sharednotes.databinding.ActivityDragAndDropBinding
import com.example.sharednotes.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class DragAndDropActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDragAndDropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movableObj.setOnLongClickListener{
            val shadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(null, shadow, it, 0)
        }

        binding.dropChipGroup.setOnDragListener(dragListener)
    }

    private val dragListener = View.OnDragListener { destinationView, draggingData ->
        val event = draggingData.action
        val movableObj = draggingData.localState // 3r parametre

        when(event){
            DragEvent.ACTION_DRAG_STARTED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENTERED -> destinationView.setBackgroundColor(Color.YELLOW)
            DragEvent.ACTION_DRAG_EXITED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENDED -> destinationView.setBackgroundColor(Color.CYAN)
            DragEvent.ACTION_DROP -> {
                if(movableObj !is Chip)
                    return@OnDragListener true
                ChipGroup::class.safeCast(movableObj.parent)?.removeView(movableObj)
                ChipGroup::class.safeCast(destinationView)?.addView(movableObj)
            }
        }
        true
    }
}