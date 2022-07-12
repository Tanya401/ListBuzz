package com.example.listbuzz.ui.ViewModel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.database.ToDoDatabase
import com.example.listbuzz.repository.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {
    private val scope = CoroutineScope(SupervisorJob())
    private val todoDao = ToDoDatabase.getDataBase(application,scope).tododao()
    private val repository : ToDoRepository=ToDoRepository(todoDao)

    val readAllToDo: LiveData<List<ToDoModal>> = repository.readAllData
    val sortByHighPriority: LiveData<List<ToDoModal>> = repository.sortByHighPriority
    val sortByMediumPriority: LiveData<List<ToDoModal>> = repository.sortByMediumPriority
    val sortByLowPriority: LiveData<List<ToDoModal>> = repository.sortByLowPriority
    val updatedTodo: LiveData<List<ToDoModal>> = repository.readCompletedTodo

    fun addTodo(todoModal: ToDoModal){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTodo(todoModal)
        }
    }
    fun deleteTodo(todoModal: ToDoModal){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTodo(todoModal)
        }
    }
    fun updateTodo(todoModal: ToDoModal){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTodo(todoModal)
        }
    }
    fun onTodoChecked(todoModal: ToDoModal,isChecked:Boolean){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTodo(todoModal.copy(todoCompleted = isChecked))
        }
    }
    fun searchDatabase(query:String):LiveData<List<ToDoModal>>{

            return repository.searchDatabase(query)
    }
    fun deleteCompletedTodo(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCompletedTodo()
        }
    }
}