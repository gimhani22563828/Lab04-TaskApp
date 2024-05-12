package com.example.taskapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private var tasks:List<Task>, context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    private val db:myDatabase = myDatabase(context)
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        val addDescription: TextView = itemView.findViewById(R.id.addDescription)
        val addPriority: TextView = itemView.findViewById(R.id.addPriority)
        val addTags: TextView = itemView.findViewById(R.id.addTags)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val t = tasks[position]
        holder.taskTextView.text = t.task
        holder.addDescription.text = t.description
        holder.addPriority.text = t.priority
        holder.addTags.text = t.tags


        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTask::class.java).apply {
                putExtra("task_id", t.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteTask(t.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newTasks: List<Task>){
        tasks = newTasks
        notifyDataSetChanged()
    }
}