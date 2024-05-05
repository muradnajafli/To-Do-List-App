package com.muradnajafli.todolistapp.presentation.todo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muradnajafli.todolistapp.R
import com.muradnajafli.todolistapp.presentation.settings.SettingsActivity
import com.muradnajafli.todolistapp.domain.model.Todo
import com.muradnajafli.todolistapp.databinding.FragmentTodoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var todoAdapter: TodoAdapter
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeViewModel()
        setUpSettingsButton()
        setUpAddButton()
    }

    private fun setUpRecyclerView() {
        todoAdapter = TodoAdapter(
            onDeleteClicked = { todo ->
                deleteList(todo)
            },
            onEditClicked = { todo ->
                editList(todo)
            },
            onNavigateToTaskFragment = { todoId ->
                navigateToTaskFragment(todoId)
            }
        )
        binding.recyclerView.adapter = todoAdapter
    }

    private fun setUpSettingsButton() {
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
    }

    private fun setUpAddButton() {
        binding.addButton.setOnClickListener {
            showAddListDialog()
        }
    }

    private fun showAddListDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add ToDo List")

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.add)) { _, _ ->
            val enteredText = input.text.toString()

            if (enteredText.isNotEmpty()) {
                todoViewModel.addTodo(enteredText)
            }
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun editList(todo: Todo) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update ToDo")
        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("UPDATE") { _, _ ->
            val updatedText = input.text.toString()
            todoViewModel.updateTodo(todo.copy(title = updatedText))
        }
        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteList(todo: Todo) {
        todoViewModel.deleteTodoById(todo)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            todoViewModel.todos.collect {
                todoAdapter.submitList(it)
            }
        }
    }

    private fun navigateToTaskFragment(todoId: Long) {
        findNavController().navigate(
            TodoFragmentDirections.actionTodoFragmentToTaskFragment(todoId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
