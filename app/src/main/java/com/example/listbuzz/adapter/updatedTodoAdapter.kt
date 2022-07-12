package com.example.listbuzz.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.databinding.CompletedTodoRvItemBinding
import com.example.listbuzz.utiles.utiles

class updatedTodoAdapter: ListAdapter<ToDoModal, updatedTodoAdapter.MyViewHolder>(DiffUtilCallback()) {

override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):MyViewHolder{
    val binding = CompletedTodoRvItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
       return MyViewHolder(binding)
}
    @RequiresApi(Build.VERSION_CODES.M)
override fun onBindViewHolder(holder:MyViewHolder,position:Int){
    val currentItem = getItem(position)
        holder.bind(currentItem)
}
class MyViewHolder(private val binding: CompletedTodoRvItemBinding) :
RecyclerView.ViewHolder(binding.root){
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(item: ToDoModal){
        binding.apply {
            todoTextTitleRv.text = item.title
            todoDateRv.text = item.date
            todoTimeRv.text = item.time
            utiles.backgroundColor(prioritylayout,item.priority)
            priorityTextlayout.text = item.priority.name
        }
    }
}
    class DiffUtilCallback:DiffUtil.ItemCallback<ToDoModal>(){
        override fun areItemsTheSame(oldItem: ToDoModal, newItem: ToDoModal): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoModal, newItem: ToDoModal): Boolean {
            return oldItem==newItem
        }
    }
}