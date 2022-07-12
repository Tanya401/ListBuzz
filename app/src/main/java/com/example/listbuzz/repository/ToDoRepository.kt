package com.example.listbuzz.repository

import androidx.lifecycle.LiveData
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.database.ToDoDao

class ToDoRepository(private val todoDao: ToDoDao) {
    val readAllData : LiveData<List<ToDoModal>> = todoDao.displayAllTodo()
    val sortByHighPriority: LiveData<List<ToDoModal>> = todoDao.sortByHighPriority()
    val sortByMediumPriority: LiveData<List<ToDoModal>> = todoDao.sortByMediumPriority()
    val sortByLowPriority: LiveData<List<ToDoModal>> = todoDao.sortByLowPriority()
    val readCompletedTodo: LiveData<List<ToDoModal>>  = todoDao.getUpdatedTodo()

    suspend fun addTodo(todoModal: ToDoModal){
        todoDao.insertTodo(todoModal)
    }
    suspend fun deleteTodo(todoModal: ToDoModal){
        todoDao.deleteTodo(todoModal)
    }
    suspend fun updateTodo(todoModal: ToDoModal){
        todoDao.updateTodo(todoModal)
    }
    suspend fun deleteCompletedTodo(){
        todoDao.deleteUpdatedToDo()
    }
    fun searchDatabase(query:String): LiveData<List<ToDoModal>>{
        return todoDao.searchDatabase(query)
    }
}