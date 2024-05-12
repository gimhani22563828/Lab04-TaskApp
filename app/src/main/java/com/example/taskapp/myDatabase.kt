package com.example.taskapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class myDatabase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val  DATABASE_NAME = "taskapp.db"
        private const val  DATABASE_VERSION = 1
        private const val  TABLE_NAME = "alltasks"
        private const val  COLUMN_ID = "id"
        private const val  COLUMN_TASK = "task"
        private const val  COLUMN_DESCRIPTION = "description"
        private const val  COLUMN_PRIORITY = "priority"
        private const val  COLUMN_TAGS = "tags"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TASK TEXT,$COLUMN_DESCRIPTION TEXT, $COLUMN_PRIORITY TEXT,$COLUMN_TAGS TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTask(task: Task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK, task.task)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_PRIORITY,task.priority)
            put(COLUMN_TAGS,task.tags)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
            val tags = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAGS))


            val t = Task(id, task, description,priority,tags)
            tasksList.add(t)
        }
        cursor.close()
        db.close()
        return tasksList
    }

    fun updateTask(task: Task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK, task.task)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_PRIORITY, task.priority)
            put(COLUMN_TAGS, task.tags)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getTaskByID(taskId: Int): Task{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
        val tags = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAGS))


        cursor.close()
        db.close()
        return Task(id, task, description,priority,tags)
    }

    fun deleteTask(taskId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(taskId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}