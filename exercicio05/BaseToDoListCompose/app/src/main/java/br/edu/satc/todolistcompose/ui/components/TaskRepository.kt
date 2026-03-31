package br.edu.satc.todolistcompose.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val apiService: TaskApiService
) {

    fun getAllTasks(): Flow<List<TaskData>> = taskDao.getAllTasks()

    suspend fun insertTask(task: TaskData) {
        taskDao.insert(task)
        try {
            apiService.createTask(task)
        } catch (e: Exception) {
        }
    }

    suspend fun updateTask(task: TaskData) {
        taskDao.update(task)
        try {
            apiService.updateTask(task.id, task)
        } catch (e: Exception) {
        }
    }

    suspend fun deleteTask(task: TaskData) {
        taskDao.delete(task)
        try {
            apiService.deleteTask(task.id)
        } catch (e: Exception) {
        }
    }
}