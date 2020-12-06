package com.trallerd.thebank.dao

import android.util.Log
import com.trallerd.thebank.models.Records
import com.trallerd.thebank.network.services.RecordsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecordsDAO {
    val recordsRetrofit = Retrofit.Builder()
        .baseUrl(" http://192.168.100.32:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val recordsService = recordsRetrofit.create(RecordsService::class.java)

    fun getAllById(id: Long, finished: (List<Records>) -> Unit) {
        recordsService.getAllById(id).enqueue(object : Callback<List<Records>> {
            override fun onResponse(call: Call<List<Records>>, response: Response<List<Records>>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }else{
                    Log.i("TOAST", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<Records>>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }

        })
    }

    fun getRecordById(id: Long, idRec: Long, finished: (Records) -> Unit) {
        recordsService.getRecordById(id, idRec).enqueue(object : Callback<Records> {
            override fun onResponse(call: Call<Records>, response: Response<Records>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }else{
                    Log.i("TOAST", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Records>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }

        })
    }

    fun getAll(finished: (List<Records>) -> Unit) {
        recordsService.getAll().enqueue(object : Callback<List<Records>> {
            override fun onResponse(call: Call<List<Records>>, response: Response<List<Records>>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }else{
                    Log.i("TOAST", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<Records>>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }

        })

    }

    fun getAllByName(name: String, finished: (List<Records>) -> Unit) {
        recordsService.getAllByName(name).enqueue(object : Callback<List<Records>> {
            override fun onResponse(call: Call<List<Records>>, response: Response<List<Records>>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }else{
                    Log.i("TOAST", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<Records>>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }
        })


    }
    fun insert(records: Records, finished: (Records) -> Unit) {
        recordsService.insert(records).enqueue(object : Callback<Records> {
            override fun onResponse(call: Call<Records>, response: Response<Records>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }else{
                    Log.i("TOAST", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Records>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }
        })

    }

    fun update(id: Long, records: Records, finished: (Records) -> Unit) {
        recordsService.update(id, records).enqueue(object : Callback<Records> {
            override fun onResponse(call: Call<Records>, response: Response<Records>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }else{
                    Log.i("TOAST", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Records>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }
        })
    }

    fun delete(id: Long, finished: () -> Unit) {
        recordsService.delete(id).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                finished()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("TOAST", t.toString())
            }
        })
    }

}