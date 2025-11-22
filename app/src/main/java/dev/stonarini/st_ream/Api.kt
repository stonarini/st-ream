package dev.stonarini.st_ream

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Song(val id: Int, val name: String, val artist: String);


interface ApiService {
    @GET("/list")
    fun getSongs(): Call<List<Song>>;
}

object Api {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.xx.xx:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getSongs(callback: (List<Song>) -> Unit) {
        val call: Call<List<Song>> = apiService.getSongs()
        call.enqueue(object : Callback<List<Song>> {
            override fun onResponse(call: Call<List<Song>>, response: Response<List<Song>>) {
                val songs = response.body()
                if (songs != null) {
                    callback(songs)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Song>>, t: Throwable) {
                callback(emptyList())
            }
        })
    }
}
