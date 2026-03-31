package br.edu.satc.todolistcompose.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.satc.todolistcompose.ui.components.TaskCard

@Composable
fun TaskScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()

    LazyColumn {
        items(tasks) { task ->
            TaskCard(
                taskData = task,
                onTaskCheckedChange = { isChecked ->
                    viewModel.updateTask(task.copy(complete = isChecked))
                }
            )
        }
    }
}