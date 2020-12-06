package com.trallerd.thebank.dao

import android.util.Log
import android.widget.Toast
import com.trallerd.thebank.MainActivity
import com.trallerd.thebank.models.Records
import com.trallerd.thebank.network.services.RecordsService
import kotlinx.coroutines.newSingleThreadContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecordsDAO {
    val recordsRetrofit = Retrofit.Builder().baseUrl(" http://192.168.100.32:3000/").addConverterFactory(GsonConverterFactory.create()).build()
    val recordsService = recordsRetrofit.create(RecordsService::class.java)

    fun getAllById(id: Long,finished: (List<Records>) -> Unit) {
        recordsService.getAllById(id).enqueue(object : Callback<List<Records>> {
            override fun onResponse(call: Call<List<Records>>,response: Response<List<Records>>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }
            }

            override fun onFailure(call: Call<List<Records>>,t: Throwable) {

            }

        })
    }

    fun getRecordById(id: Long,idRec: Long,finished: (Records) -> Unit) {
        recordsService.getRecordById(id,idRec).enqueue(object : Callback<Records> {
            override fun onResponse(call: Call<Records>,response: Response<Records>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }
            }

            override fun onFailure(call: Call<Records>,t: Throwable) {
            }

        })
    }

    fun getAll(finished: (List<Records>) -> Unit) {
        recordsService.getAll().enqueue(object : Callback<List<Records>> {
            override fun onResponse(call: Call<List<Records>>,response: Response<List<Records>>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }

            }

            override fun onFailure(call: Call<List<Records>>,t: Throwable) {
            }

        })

    }

    fun getAllByName(name: String,finished: (List<Records>) -> Unit) {
        recordsService.getAllByName(name).enqueue(object : Callback<List<Records>> {
            override fun onResponse(call: Call<List<Records>>,response: Response<List<Records>>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }
            }

            override fun onFailure(call: Call<List<Records>>,t: Throwable) {
            }
        })


    }

    fun insert(records: Records,finished: (Records) -> Unit) {
        recordsService.insert(records).enqueue(object : Callback<Records> {
            override fun onResponse(call: Call<Records>,response: Response<Records>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }
            }

            override fun onFailure(call: Call<Records>,t: Throwable) {
            }
        })

    }

    fun update(id: Long,records: Records,finished: (Records) -> Unit) {
        recordsService.update(id,records).enqueue(object : Callback<Records> {
            override fun onResponse(call: Call<Records>,response: Response<Records>) {
                if (response.body() != null) {
                    val records = response.body()
                    finished(records!!)
                }

            }

            override fun onFailure(call: Call<Records>,t: Throwable) {
            }
        })
    }

    fun delete(id: Long,finished: () -> Unit) {
        recordsService.delete(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>,response: Response<Void>) {
                finished()
            }

            override fun onFailure(call: Call<Void>,t: Throwable) {
            }
        })
    }

}