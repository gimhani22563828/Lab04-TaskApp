package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: myDatabase
    private lateinit var tasksAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = myDatabase(this)
        tasksAdapter = TaskAdapter(db.getAllTasks(), this)

        binding.tasksRView.layoutManager = LinearLayoutManager(this)
        binding.tasksRView.adapter = tasksAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, CreateTask::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        tasksAdapter.refreshData(db.getAllTasks())
    }
}