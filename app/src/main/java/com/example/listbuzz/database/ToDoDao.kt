package com.example.listbuzz.database

import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.listbuzz.data.ToDoModal


@Dao
interface ToDoDao {
    @Query("SELECT * FROM TO_DO_TABLE WHERE isCompleted = 0")
    fun displayAllTodo(): LiveData<List<ToDoModal>>

    @Query("SELECT * FROM TO_DO_TABLE WHERE isCompleted = 1")
    fun getUpdatedTodo(): LiveData<List<ToDoModal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todoModal:ToDoModal)

    @Update
    suspend fun updateTodo(todoModal:ToDoModal)

    @Delete
    suspend fun deleteTodo(todoModal:ToDoModal)

    @Query("DELETE FROM to_do_table WHERE isCompleted = 1")
    suspend fun deleteUpdatedToDo()

    @Query("SELECT * FROM to_do_table WHERE isCompleted = 0 AND title LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoModal>>

    @Query("SELECT * FROM to_do_table  WHERE isCompleted = 0 ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
     fun sortByHighPriority(): LiveData<List<ToDoModal>>

    @Query("SELECT * FROM to_do_table  WHERE isCompleted = 0 ORDER BY CASE WHEN priority LIKE 'M%' THEN 1 WHEN priority LIKE 'L%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByMediumPriority(): LiveData<List<ToDoModal>>

    @Query("SELECT * FROM to_do_table  WHERE isCompleted = 0 ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
     fun sortByLowPriority(): LiveData<List<ToDoModal>>

     @Query("DELETE FROM to_do_table")
     fun deleteAll()
}