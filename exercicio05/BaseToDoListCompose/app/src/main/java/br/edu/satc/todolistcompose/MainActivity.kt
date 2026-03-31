package br.edu.satc.todolistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import br.edu.satc.todolistcompose.data.TaskApiService
import br.edu.satc.todolistcompose.data.TaskDatabase
import br.edu.satc.todolistcompose.data.TaskRepository
import br.edu.satc.todolistcompose.ui.TaskViewModel
import br.edu.satc.todolistcompose.ui.TaskViewModelFactory
import br.edu.satc.todolistcompose.ui.screens.HomeScreen
import br.edu.satc.todolistcompose.ui.theme.ToDoListComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = TaskDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(db.taskDao(), TaskApiService.create())
        val viewModel: TaskViewModel by viewModels {
            TaskViewModelFactory(repository)
        }
        setContent {
            ToDoListComposeTheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}