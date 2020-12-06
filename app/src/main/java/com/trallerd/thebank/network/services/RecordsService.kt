package com.trallerd.thebank.network.services

import com.trallerd.thebank.models.Records
import retrofit2.Call
import retrofit2.http.*

interface RecordsService {
    @GET("Records")
    fun getAllById(@Query("fk_user") id: Long): Call<List<Records>>

    @GET("Records")
    fun getRecordById(@Query("fk_user") id: Long, @Query("id") idRec: Long): Call<Records>

    @GET("Records")
    fun getAll(): Call<List<Records>>

    @GET("Records")
    fun getAllByName(@Query("person") name: String): Call<List<Records>>

    @POST("Records")
    @Headers("Content-Type: application/json")
    fun insert(@Body record: Records): Call<Records>

    @PATCH("Records/{id}")
    @Headers("Content-Type : application/json")
    fun update(@Path("id") id: Long, @Body record: Records): Call<Records>

    @DELETE("Records/{id}")
    fun delete(@Path("id") id: Long): Call<Void>

}