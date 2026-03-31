package br.edu.satc.todolistcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.edu.satc.todolistcompose.data.TaskData
import br.edu.satc.todolistcompose.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val tasks: StateFlow<List<TaskData>> = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insertTask(task: TaskData) {
        viewModelScope.launch { repository.insertTask(task) }
    }
    fun updateTask(task: TaskData) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TaskViewModel(repository) as T
    }
}