package com.example.listbuzz.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.databinding.TodoRvItemBinding
import com.example.listbuzz.utiles.utiles


class ToDoAdapter(private val listener: OnItemClickListener) :
    ListAdapter<ToDoModal, ToDoAdapter.MyViewHolder>(DiffUtilCallback())
{
    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder{
        val binding =
            TodoRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

@RequiresApi(Build.VERSION_CODES.M)
  override fun onBindViewHolder(holder:MyViewHolder,position:Int){
      val currentItem = getItem(position)
    holder.bind(currentItem)
  }
    inner class MyViewHolder(private val binding: TodoRvItemBinding):
            RecyclerView.ViewHolder(binding.root){
                init{
                    binding.apply {
                        root.setOnClickListener{
                            val position = adapterPosition
                            if( position !=RecyclerView.NO_POSITION){
                                val todo = getItem(position)
                                listener.onItemClick(todo)
                            }
                        }
                        todoDoneCheckBox.setOnClickListener {
                            val position =  adapterPosition
                            if( position != RecyclerView.NO_POSITION){
                                val todoCheck = getItem(position)
                                listener.onCheckBoxClickListener(
                                    todoCheck,todoDoneCheckBox.isChecked
                                )
                            }
                        }
                    }
                }
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item:ToDoModal){
            binding.apply{
                todoTextTitleRv.text = item.title
                todoDateRv.text=item.date
                todoTimeRv.text = item.time
                reminderImage.isVisible=item.addReminderForToDo
                priorityTextlayout.text=item.priority.name
                utiles.backgroundColor(prioritylayout, item.priority)
                todoDoneCheckBox.isChecked=item.todoCompleted
            }
        }
            }
    class DiffUtilCallback:DiffUtil.ItemCallback<ToDoModal>(){
        override fun areItemsTheSame(oldItem: ToDoModal, newItem: ToDoModal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoModal, newItem: ToDoModal): Boolean {
            return oldItem==newItem
        }
    }
     interface OnItemClickListener{
         fun onItemClick(todoModal:ToDoModal)
         fun onCheckBoxClickListener(todoModal:ToDoModal,isChecked:Boolean)
     }
}