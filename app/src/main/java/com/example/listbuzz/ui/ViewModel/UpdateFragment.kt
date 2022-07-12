package com.example.listbuzz.ui.ViewModel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.listbuzz.R
import com.example.listbuzz.alarm.SetAlarm
import com.example.listbuzz.data.Priority
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.databinding.FragmentUpdateBinding
import com.example.listbuzz.ui.ViewModel.viewmodel.ToDoViewModel
import com.example.listbuzz.utiles.transformDatePicker
import com.example.listbuzz.utiles.transformTimePicker
import com.example.listbuzz.utiles.utiles.priority
import com.example.listbuzz.utiles.utiles.selectPriority
import java.util.*

class updateFragment: Fragment(R.layout.fragment_update) {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ToDoViewModel by viewModels()
    private val args by navArgs<updateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdateBinding.bind(view)

        val todoModal = args.currentTodo
        if (todoModal != null) {
            loadData(todoModal)
        }
        initView()
        binding.updateTodo.setOnClickListener {
            saveUpdatedTodo()
        }
    }

    private fun loadData(todoModal: ToDoModal) = with(binding) {
        updateToDoLayout.apply {
            editInputLayout.setText(todoModal.title)
            dateEdt.setText(todoModal.date)
            timeEdt.setText(todoModal.time)
            etPrioritySpinner.setText(todoModal.priority.name)
            reminderSwitch.isChecked = todoModal.addReminderForToDo
        }
    }

    private fun initView() {
        val priorityAdapter = ArrayAdapter(requireContext(),
            R.layout.item_auto_complete_dropdown, priority)
        binding.updateToDoLayout.apply {
            etPrioritySpinner.setAdapter(priorityAdapter)
            dateEdt.transformDatePicker(requireContext(), "dd/MM/yyyy", Date())
            timeEdt.transformTimePicker(requireContext(), "h:mm a")
        }
    }
  private fun saveUpdatedTodo(){
      binding.updateToDoLayout.apply {
      val (title,date,time,_,reminder) = updateTodo()

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
              else -> {
                  viewModel.updateTodo(updateTodo())
                  if(reminder) {
                      if (reminder) {
                          SetAlarm.setAlarmReminderForTodo(requireContext(), updateTodo())
                      }
                  }
                  Toast.makeText(requireContext(),"Todo Updated Successfully",Toast.LENGTH_LONG).show()
                  findNavController().navigateUp()
              }
          }
      }
  }
    private fun updateTodo():ToDoModal {
        binding.updateToDoLayout.let {
            val titleofTodo =  it.editInputLayout.text.toString()
            val date = it.dateEdt.text.toString()
            val time = it.timeEdt.text.toString()
            val reminder = it.reminderSwitch.isChecked
            val prioritytxt = it.etPrioritySpinner.text.toString()


            val priority: Priority = if(prioritytxt == args.currentTodo?.priority?.name){
                args.currentTodo!!.priority
            }else{
                selectPriority(prioritytxt)
            }
            val id = args.currentTodo!!.id
            return ToDoModal(
                title = titleofTodo,
                date = date,
                time = time,
                priority=priority,
                addReminderForToDo = reminder,
                id = id
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}