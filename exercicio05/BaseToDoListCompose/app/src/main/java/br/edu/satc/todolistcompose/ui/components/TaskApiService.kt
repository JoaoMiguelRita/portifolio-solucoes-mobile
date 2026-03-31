package br.edu.satc.todolistcompose.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TaskApiService {

    @GET("tasks")
    suspend fun getAllTasks(): Response<List<TaskData>>

    @POST("tasks")
    suspend fun createTask(@Body task: TaskData): Response<TaskData>

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Int,
        @Body task: TaskData
    ): Response<TaskData>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>

    companion object {
        private const val BASE_URL = "https://sua-api.com/" // ← troque aqui

        fun create(): TaskApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaskApiService::class.java)
        }
    }
}