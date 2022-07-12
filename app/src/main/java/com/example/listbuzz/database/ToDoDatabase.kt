package com.example.listbuzz.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.listbuzz.data.Converter
import com.example.listbuzz.data.Priority
import com.example.listbuzz.data.ToDoModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [ToDoModal::class], version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase() {
  abstract fun tododao():ToDoDao

  private class ToDoDatabaseCallBack(private val scope: CoroutineScope):RoomDatabase.Callback(){
      override fun onCreate(db: SupportSQLiteDatabase){
          super.onCreate(db)
          INSTANCE.let{ toDoDatabase->
              scope.launch {
                  val todoDao = toDoDatabase?.tododao()
                  todoDao?.deleteAll()
                  //add tasks
                  val todo1 = ToDoModal("Kotlin class","10/07/22","06:00 PM",
                  priority = Priority.Medium,addReminderForToDo = true,todoCompleted = false)
                  val todo2 = ToDoModal("Internship Meet","08/07/22","02:00 PM",
                      priority = Priority.High,addReminderForToDo = true,todoCompleted = false)
                  val todo3 = ToDoModal("Presentation","15/07/22","08:00 PM",
                      priority = Priority.High,addReminderForToDo = true,todoCompleted = false)
                  val todo4 = ToDoModal("Get Veggies","06/07/22","05:00 PM",
                      priority = Priority.Low,addReminderForToDo = false,todoCompleted = false)
                  todoDao?.insertTodo(todo1)
                  todoDao?.insertTodo(todo2)
                  todoDao?.insertTodo(todo3)
                  todoDao?.insertTodo(todo4)
              }

          }
      }
  }
    companion object{
        @Volatile
        private var INSTANCE: ToDoDatabase?=null
        fun getDataBase(context: Context,scope:CoroutineScope):ToDoDatabase{
            return INSTANCE?: synchronized(this){
                val instence = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,"to_do_database"
                ).addCallback(ToDoDatabaseCallBack(scope))
                    .build()
                INSTANCE = instence
                instence

            }
        }
    }
}