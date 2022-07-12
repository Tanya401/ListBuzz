package com.example.listbuzz.ui.ViewModel

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.listbuzz.R
import com.example.listbuzz.ui.ViewModel.viewmodel.ToDoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteDialog:DialogFragment(){
    private val viewModel : ToDoViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setIcon(R.drawable.ic_baseline_delete_forever_24)
            .setPositiveButton(getString(R.string.dialog_yes_button)) {_, _ ->
                viewModel.deleteCompletedTodo()
                Toast.makeText(requireContext(),"All completed Todo deleted successfully",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.dialog_no_button)) {dialog, _ ->
                dialog.dismiss()
                Toast.makeText(requireContext(),"Dialog Dismissed",Toast.LENGTH_SHORT).show()
            }.create()
}