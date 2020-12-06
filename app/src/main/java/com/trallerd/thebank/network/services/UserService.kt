package com.trallerd.thebank.network.services

import com.trallerd.thebank.models.Users
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("Users")
    fun getUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<List<Users>>

    @POST("Users")
    @Headers("Content-Type: application/json")
    fun insert(@Body user: Users): Call<Users>

    @DELETE("Users/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}