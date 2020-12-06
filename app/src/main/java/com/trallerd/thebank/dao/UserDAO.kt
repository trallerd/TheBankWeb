package com.trallerd.thebank.dao

import android.util.Log
import com.trallerd.thebank.models.Users
import com.trallerd.thebank.network.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDAO {
    val userRetrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.32:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val userService = userRetrofit.create(UserService::class.java)


    fun getUser(username: String, password: String, finished: (Users) -> Unit) {
        userService.getUser(username, password).enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                if (response.body() != null) {
                    val users = response.body()!!.first()
                    finished(users)
                } 
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }


        })
    }

    fun insert(user: Users, finished: (Users) -> Unit) {
        userService.insert(user).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.body() != null) {
                    val users = response.body()
                    finished(users!!)
                } else {
                    Log.i("TOAST", response.body().toString())
                }

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }

        })

    }

    fun delete(user: Users, finished: () -> Unit) {
        userService.delete(user.id!!).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                finished()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }

        })
    }

}