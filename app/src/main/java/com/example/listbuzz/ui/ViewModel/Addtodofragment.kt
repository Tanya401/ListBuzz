package com.example.listbuzz.ui.ViewModel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.listbuzz.R
import com.example.listbuzz.alarm.SetAlarm
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.databinding.FragmentAddtodofragmentBinding
import com.example.listbuzz.ui.ViewModel.viewmodel.ToDoViewModel
import com.example.listbuzz.utiles.transformDatePicker
import com.example.listbuzz.utiles.transformTimePicker
import com.example.listbuzz.utiles.utiles.selectPriority
import com.example.listbuzz.utiles.utiles.priority

import java.util.*

class addtodofragment : Fragment(R.layout.fragment_addtodofragment){
    private var _binding: FragmentAddtodofragmentBinding?=null
    private val binding get() = _binding!!
   private val viewModel: ToDoViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddtodofragmentBinding.bind(view)
        initView()
    }

    private fun initView() {
     val priorityAdapter = ArrayAdapter(
         requireContext(),
         R.layout.item_auto_complete_dropdown,priority)
        binding.addtodolayout.apply {
            etPrioritySpinner.setAdapter(priorityAdapter)
            dateEdt.transformDatePicker(requireContext(),"dd/MM/yy", Date())
            timeEdt.transformTimePicker(requireContext(),"h:mm a")
        }
        binding.saveTodofab.setOnClickListener{
            insertToDoIntoDatabase()
        }
    }

    private fun insertToDoIntoDatabase() {
        val (title,date,time,_,reminder) = addTodo()
        with(binding.addtodolayout){
            when{
                title.isEmpty() -> {
                     this.editInputLayout.error = getString(R.string.error_msg_for_title_tinl)
                }
                date.isEmpty() -> {
                 this.dateEdt.error = getString(R.string.date_error)
                }
                time.isEmpty() -> {
                 this.timeEdt.error = getString(R.string.time_error)
                }
                else ->{
                  viewModel.addTodo(addTodo())
                        if(reminder){
                            SetAlarm.setAlarmReminderForTodo(requireContext(), addTodo())
                        }

                    Toast.makeText(requireContext(),"ToDo added successfully", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addTodo(): ToDoModal = binding.addtodolayout.let {
      val titleOfTodo = it.editInputLayout.text.toString()
        val date = it.dateEdt.text.toString()
        val time = it.timeEdt.text.toString()
        val reminder = it.reminderSwitch.isChecked
        val priorityTxt = it.etPrioritySpinner.text.toString()
        val priority = selectPriority(priorityTxt)
        return ToDoModal(titleOfTodo,date,time,priority,reminder)
    }
}