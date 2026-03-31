package br.edu.satc.todolistcompose.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TaskData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskData)

    @Update
    suspend fun update(task: TaskData)

    @Delete
    suspend fun delete(task: TaskData)
}