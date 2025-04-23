package com.example.myfirstapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.sql.Timestamp
import java.util.UUID

data class TodoItem(
    val id: String = "",
    val sortId: Int = 0,
    val title: String,
    val completed: Boolean = false,
    val fechaCompletado: Timestamp? = null
) {
}

class TodoViewModel : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList

    private val _todosNotDone = MutableStateFlow<List<TodoItem>>(emptyList())
    val todosNotDone: StateFlow<List<TodoItem>> = _todosNotDone

    private val _todoDone = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoDone: StateFlow<List<TodoItem>> = _todoDone

    init {
        initializeTodos() // Call the method to add predefined tasks
        updateTodoLists()
    }

    private fun initializeTodos() {
        val sampleTasks = listOf(
            TodoItem(id = UUID.randomUUID().toString(), sortId = 1, title = "Buy groceries"),
            TodoItem(id = UUID.randomUUID().toString(), sortId = 2, title = "Walk the dog"),
            TodoItem(id = UUID.randomUUID().toString(), sortId = 3, title = "Finish the report"),
            TodoItem(id = UUID.randomUUID().toString(), sortId = 4, title = "Call mom")
        )
        _todoList.value = sampleTasks
    }

    fun addTodo(title: String) {
        Log.i("TodoViewModel", "Adding todo...")

        val maxSortId = _todosNotDone.value
            .filter { it.completed.not() }
            .fold(0) { acc, todo -> maxOf(acc, todo.sortId) }

        val newTodo = TodoItem(
            id = UUID.randomUUID().toString(), // Use UUID for unique ID
            sortId = maxSortId + 1,
            title = title,
            completed = false,
            fechaCompletado = null
        )

        _todoList.value = _todoList.value + newTodo
        updateTodoLists()
    }

    private fun updateTodoLists() {
        _todosNotDone.value = _todoList.value.filterNot { it.completed }.sortedByDescending { it.sortId }
        _todoDone.value = _todoList.value.filter { it.completed }.sortedByDescending { it.fechaCompletado }
    }

    fun updateTodoStatus(todo: TodoItem, completed: Boolean) {
        Log.i("TodoViewModel", "Update todo...")

        _todoList.value = _todoList.value.map {
            if (it.id == todo.id) {
                it.copy(completed = completed, fechaCompletado = if (completed) Timestamp(System.currentTimeMillis()) else null)
            } else {
                it
            }
        }
        updateTodoLists()
    }

    fun deleteTodo(todo: TodoItem) {
        Log.d("TodoViewModel", "Delete todo...")

        _todoList.value = _todoList.value.filter { it.id != todo.id }
        updateTodoLists()
    }

    fun floatItem(item: TodoItem) {
        Log.d("TodoViewModel", "float item ${item.title}, ${item.sortId}")

        val itemIndex = _todosNotDone.value.indexOf(item)
        if (itemIndex > 0) {
            val targetItem = _todosNotDone.value[itemIndex - 1]
            swapItemPosition(item, targetItem)
        }
    }

    fun sinkItem(item: TodoItem) {
        Log.d("TodoViewModel", "sink item ${item.title}, ${item.sortId}")

        val itemIndex = _todosNotDone.value.indexOf(item)
        if (itemIndex < _todosNotDone.value.size - 1) {
            val targetItem = _todosNotDone.value[itemIndex + 1]
            swapItemPosition(item, targetItem)
        }
    }

    private fun swapItemPosition(originItem: TodoItem, targetItem: TodoItem) {
        Log.d("TodoViewModel", "Swap items ${originItem.title}, ${originItem.sortId} by ${targetItem.title}, ${targetItem.sortId}")

        if (originItem != targetItem) {
            _todoList.value = _todoList.value.map {
                when (it.id) {
                    originItem.id -> it.copy(sortId = targetItem.sortId)
                    targetItem.id -> it.copy(sortId = originItem.sortId)
                    else -> it
                }
            }
            updateTodoLists()
        }
    }
}