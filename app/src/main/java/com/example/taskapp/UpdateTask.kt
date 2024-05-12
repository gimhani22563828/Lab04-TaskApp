package com.example.taskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.taskapp.databinding.ActivityUpdateTaskBinding

class UpdateTask : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: myDatabase
    private var taskId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = myDatabase(this)

        taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.updateTaskEditText.setText(task.task)
        binding.updateaddDescription.setText(task.description)
        binding.updateaddPriority.setText(task.priority)
        binding.updateaddTags.setText(task.tags)


        binding.updateSaveButton.setOnClickListener {
            val newTask = binding.updateTaskEditText.text.toString()
            val newDescription = binding.updateaddDescription.text.toString()
            val newPriority = binding.updateTaskEditText.text.toString()
            val newTags = binding.updateTaskEditText.text.toString()

            val updatedTask = Task(taskId, newTask, newDescription,newPriority,newTags)
            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
