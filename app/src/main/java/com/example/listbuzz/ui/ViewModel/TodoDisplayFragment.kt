package com.example.listbuzz.ui.ViewModel

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listbuzz.R
import com.example.listbuzz.adapter.ToDoAdapter
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.databinding.FragmentTodoDisplayBinding
import com.example.listbuzz.ui.ViewModel.viewmodel.ToDoViewModel
import com.example.listbuzz.utiles.observeOnce
import com.google.android.material.snackbar.Snackbar


class todoDisplayFragment : Fragment(R.layout.fragment_todo_display), ToDoAdapter.OnItemClickListener {
    companion object {
        private const val TAG = "todoDisplayFragment"
    }

    private val viewModel: ToDoViewModel by viewModels()
    private var _binding: FragmentTodoDisplayBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: ToDoAdapter by lazy { ToDoAdapter(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTodoDisplayBinding.bind(view)
        binding.apply {
            todoRv.apply {
                adapter = mAdapter
                todoRv.layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                swipeToDelete(todoRv)
            }
            viewModel.readAllToDo.observe(viewLifecycleOwner) { todoList ->
                if (todoList.isNotEmpty()) {
                    todoRv.isVisible = true
                    noTodotxt.isVisible = false
                    mAdapter.submitList(todoList)
                    Log.d(TAG, "We got result from database $todoList")
                } else {
                    todoRv.isVisible = false
                    noTodotxt.isVisible = true
                }
            }
            addnewTodo.setOnClickListener {
                findNavController().navigate(R.id.action_todoDisplayFragment_to_addtodofragment3)
            }
        }
        setHasOptionsMenu(true)
    }



    //go to update the clicked todo
    override fun onItemClick(toDoModal: ToDoModal){
        val action = todoDisplayFragmentDirections.actionTodoDisplayFragmentToUpdateFragment(currentTodo = toDoModal)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClickListener(toDoModal: ToDoModal,isChecked:Boolean){
          viewModel.onTodoChecked(toDoModal,isChecked)
        Snackbar.make(requireView(),"Task Completed Successfully!!",Snackbar.LENGTH_LONG).show()
    }

    //deleting task on swipe
    private fun swipeToDelete(recyclerView: RecyclerView) {
     val swipeToDeleteCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
         override fun onMove(
             recyclerView: RecyclerView,
             viewHolder: RecyclerView.ViewHolder,
             target: RecyclerView.ViewHolder
         ): Boolean {
             return false
         }

         override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             val deleteTodo = mAdapter.currentList[viewHolder.adapterPosition]
             viewModel.deleteTodo(deleteTodo)
             restoredefault(viewHolder.itemView,deleteTodo)
         }
     }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun restoredefault(view: View, deleteItem: ToDoModal){
        val snackbar =  Snackbar.make(view,"Task deleted successfully!!",Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO"){
            viewModel.addTodo(deleteItem)
        }
        snackbar.show()
    }
    private fun searchDatabase(searchTxt: String){
        viewModel.searchDatabase(searchTxt).observeOnce(viewLifecycleOwner,{ searchList->
            mAdapter.submitList(searchList)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu,menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView =searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object:  androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null)
                {
                    searchDatabase(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    searchDatabase(newText)
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sort_by_high_priority -> {
                viewModel.sortByHighPriority.observe(viewLifecycleOwner,{
                    mAdapter.submitList(it)
                })
            }
            R.id.sort_by_medium_priority -> {
                viewModel.sortByMediumPriority.observe(viewLifecycleOwner,{
                    mAdapter.submitList(it)
                })
            }
            R.id.sort_by_low_priority -> {
                viewModel.sortByLowPriority.observe(viewLifecycleOwner,{
                    mAdapter.submitList(it)
                })
            }
        }
        item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


