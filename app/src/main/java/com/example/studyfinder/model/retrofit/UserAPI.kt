package com.example.studyfinder.model.retrofit

import com.example.studyfinder.model.User
import okhttp3.Connection
import okhttp3.internal.http.hasBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @HTTP(method = "GET", path = "/users")
    suspend fun getAllUsers(): Response<List<User>>

    @HTTP(method = "GET", path = "/users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<User>

    @HTTP(method = "POST", path = "/users", hasBody = true)
    suspend fun saveUser(@Body user: User): Response<User>

    @HTTP(method = "PUT", path = "/users/{email}", hasBody = true)
    suspend fun addCourseToUser(@Path("email") email: String, @Body course: String):Response<User>

    @HTTP(method = "GET", path = "/users")
    suspend fun getUsersByCourse(@Query("course") course: String): Response<List<User>>

    @HTTP(method = "DELETE", path = "/users/{email}", hasBody = true)
    suspend fun deleteCourseFromUser(@Path("email") email: String, @Body course: String):Response<User>
}
val userAPI = retrofit.create(UserAPI::class.java)