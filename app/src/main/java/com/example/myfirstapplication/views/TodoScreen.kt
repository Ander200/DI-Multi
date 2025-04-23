package com.example.myfirstapplication.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.R
import com.example.myfirstapplication.viewModels.TodoItem
import com.example.myfirstapplication.viewModels.TodoViewModel

@Composable
fun TodoApp(viewModel: TodoViewModel) {
    // Collecting the state from the ViewModel
    val todoDone by viewModel.todoDone.collectAsState()
    val todosNotDone by viewModel.todosNotDone.collectAsState()

    var newTodoTitle by remember { mutableStateOf("") }
    var todoDoneExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header for the To-Do list
        Text(
            "TODOs",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Input for adding a new task
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // TextField for entering new task title
            TextField(
                value = newTodoTitle,
                onValueChange = { newTodoTitle = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Enter new task") }
            )
            Button(
                onClick = {
                    if (newTodoTitle.isNotEmpty()) {
                        viewModel.addTodo(newTodoTitle)
                        newTodoTitle = "" // Clear the input field after adding
                    }
                }
            ) {
                Text("Agregar")
            }
        }

        // List of tasks
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = todosNotDone,
                key = { it.id } // Ensure this is unique
            ) { todo ->
                SortableTodoItemView(
                    todo = todo,
                    alfa = 1.0f,
                    onCheckedChange = {
                        viewModel.updateTodoStatus(todo, it)
                    },
                    onDeleteClick = {
                        viewModel.deleteTodo(todo)
                    },
                    onFloatClick = {
                        viewModel.floatItem(todo)
                    },
                    onSinkClick = {
                        viewModel.sinkItem(todo)
                    }
                )
            }

            item {
                HorizontalDivider()
            }

            if (todoDoneExpanded) {
                // Expand button
                item {
                    ToggleExpandButton(
                        { todoDoneExpanded = !todoDoneExpanded },
                        R.drawable.baseline_keyboard_arrow_down_24
                    )
                }

                // Completed tasks list
                items(
                    items = todoDone,
                    key = { it.id }
                ) { todo ->
                    TodoItemView(
                        todo = todo,
                        alfa = 0.6f,
                        onCheckedChange = {
                            viewModel.updateTodoStatus(todo, it)
                        },
                        onDeleteClick = {
                            viewModel.deleteTodo(todo)
                        },
                    )
                }
            } else {
                // Collapse button
                item {
                    ToggleExpandButton(
                        { todoDoneExpanded = !todoDoneExpanded },
                        R.drawable.baseline_keyboard_arrow_up_24
                    )
                }
            }
        }
    }
}


@Composable
fun ToggleExpandButton(todoDoneExpanded: () -> Unit, id: Int) {
    IconButton(
        onClick = todoDoneExpanded, modifier = Modifier
            .padding(2.dp) // Padding del botón
    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = "Icono para explandir la lista de elementos completados",
            modifier = Modifier
                .size(24.dp)
                .alpha(0.6f)
        )
    }
}

@Composable
fun SortableTodoItemView(
    todo: TodoItem,
    alfa: Float,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onFloatClick: () -> Unit,
    onSinkClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .alpha(alfa)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(1.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp) // or your desired button size
                    .clickable(onClick = onFloatClick)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
                    contentDescription = "Icono para hundir elementos",
                    modifier = Modifier
                        .size(22.dp)
                        .alpha(0.6f)
                        .align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .size(22.dp) // or your desired button size
                    .clickable(onClick = onSinkClick)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = "Icono flotar elementos",
                    modifier = Modifier
                        .size(22.dp)
                        .alpha(0.6f)
                        .align(Alignment.Center)
                )
            }
        }
        Checkbox(
            checked = todo.completed,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = todo.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = if (todo.completed) {
                TextStyle(textDecoration = TextDecoration.LineThrough)
            } else {
                TextStyle.Default
            }
        )
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}

@Composable
fun TodoItemView(
    todo: TodoItem,
    alfa: Float,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .alpha(alfa)
    ) {
        Checkbox(
            checked = todo.completed,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = todo.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = if (todo.completed) {
                TextStyle(textDecoration = TextDecoration.LineThrough)
            } else {
                TextStyle.Default
            }
        )
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}