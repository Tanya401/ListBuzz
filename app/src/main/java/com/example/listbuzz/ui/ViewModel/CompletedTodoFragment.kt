package com.example.listbuzz.ui.ViewModel

import android.graphics.*
import android.os.Bundle
import android.view.*

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listbuzz.R
import com.example.listbuzz.adapter.updatedTodoAdapter
import com.example.listbuzz.databinding.FragmentCompletedTodoBinding
import com.example.listbuzz.ui.ViewModel.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_completed_todo.*


class completedTodoFragment: Fragment(R.layout.fragment_completed_todo){
private var _binding: FragmentCompletedTodoBinding?=null
    private val binding get() = _binding!!
    private val viewModel: ToDoViewModel by viewModels()
    private val mAdapter: updatedTodoAdapter by lazy { updatedTodoAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompletedTodoBinding.bind(view)

        binding.apply {
            updatelayoutRv.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.updatedTodo.observe(viewLifecycleOwner, {todoList->
            mAdapter.submitList(todoList)
        })
        deletedToDoSwipe()
        setHasOptionsMenu(true)
    }

    private fun deletedToDoSwipe() {
      val simpleItemTouchCallBack = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
          override fun onMove(
              recyclerView: RecyclerView,
              viewHolder: RecyclerView.ViewHolder,
              target: RecyclerView.ViewHolder
          ): Boolean = false

          override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
              if(direction == ItemTouchHelper.LEFT){
                val deletedTodo = mAdapter.currentList[viewHolder.adapterPosition]
                  viewModel.deleteTodo(deletedTodo)
              }
              else if(direction == ItemTouchHelper.RIGHT){
                  val deletedTodo = mAdapter.currentList[viewHolder.adapterPosition]
                  viewModel.deleteTodo(deletedTodo)
              }
          }

          override fun onChildDraw(
              canvas: Canvas,
              recyclerView: RecyclerView,
              viewHolder: RecyclerView.ViewHolder,
              dX: Float,
              dY: Float,
              actionState: Int,
              isCurrentlyActive: Boolean
          ) {
              if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                  val itemView = viewHolder.itemView
                  val paint = Paint()
                  val icon:Bitmap
                  if(dX >0){
                    icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                      paint.color = Color.parseColor("#D32F2F")
                      canvas.drawRect(
                       itemView.left.toFloat(), itemView.top.toFloat(),
                          itemView.left.toFloat()+dX, itemView.bottom.toFloat(), paint
                      )
                     canvas.drawBitmap(icon,
                         itemView.left.toFloat(), itemView.top.toFloat()+(itemView.bottom.toFloat() - itemView .top.toFloat() - icon.height.toFloat()) /2,
                         paint
                     )
                  }
                  else{
                    icon= BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                      paint.color = Color.parseColor("D32F2F")
                      canvas.drawRect(
                          itemView.right.toFloat()+dX, itemView.top.toFloat(),
                          itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                      )
                      canvas.drawBitmap(icon,
                      itemView.right.toFloat()-icon.width,
                      itemView.top.toFloat() +(itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) /2,
                          paint
                      )
                  }
                  viewHolder.itemView.translationX = dX
              }
              else{
                  super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
              }
          }
      }
        val itemTouchHelper  = ItemTouchHelper(simpleItemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(updatelayoutRv)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.string.delete_all -> findNavController().navigate(R.id.action_global_deleteDialog2)
        }
        return super.onOptionsItemSelected(item)
    }
}