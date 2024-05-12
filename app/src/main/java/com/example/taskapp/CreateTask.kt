package com.example.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskapp.databinding.ActivityCreateTaskBinding

class CreateTask : AppCompatActivity() {

    //declare binding variable
    private lateinit var binding: ActivityCreateTaskBinding

    //declare a variable for db helper
    private lateinit var db: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate the layout
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize db helper
        db = myDatabase(this)

        binding.saveButton.setOnClickListener {
            //retrieve task name and content
            val task = binding.taskEditText.text.toString()
            val description = binding.addDescription.text.toString()
            val priority = binding.addPriority.text.toString()
            val tags = binding.addTags.text.toString()


            //create task object
            val t = Task(0, task, description,priority,tags)

            //insert task object to db
            db.insertTask(t)
            finish()
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
        }
    }
}