package com.muradnajafli.todolistapp.presentation.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.muradnajafli.todolistapp.R
import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.databinding.FragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TaskViewModel by viewModels()
    private val args: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeViewModel()
        setOnClickListeners()
        taskViewModel.getTasks(args.listId)
    }

    private fun setUpRecyclerView() {
        taskAdapter = TaskAdapter(
            onDeleteClick = { task ->
                showDeleteTaskDialog(task)
            },
            onEditClick = { task ->
                showEditTaskDialog(task)
            },
            onUpdate = { task ->
                taskViewModel.updateTask(task)
            }
        )
        binding.taskRecyclerView.adapter = taskAdapter
    }

    private fun setOnClickListeners() {
        binding.addButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.tasks.collect { tasks ->
                taskAdapter.submitList(tasks)
            }
        }

    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.add_todo_item))

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.add)) { dialog, _ ->
            val enteredText = input.text.toString()
            val todoId = args.listId

            taskViewModel.addTask(
                Task(
                    taskName = enteredText,
                    todoId = todoId
                )
            )
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showEditTaskDialog(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.update_todo_task))
        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.update)) { _, _ ->
            val updatedText = input.text.toString()

            taskViewModel.updateTask(
                task.copy(
                    taskName = updatedText
                )
            )
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showDeleteTaskDialog(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete_todo_task))

        builder.setPositiveButton(getString(R.string.delete)) { _, _ ->
            taskViewModel.deleteTask(task)
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}