package com.sample.simpsonsviewer.network

import com.sample.simpsonsviewer.model.IconModel
import com.sample.simpsonsviewer.model.SimpsonCharModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {


    @GET("?q=simpsons+characters&format=json")
    fun getCharacters(): Call<SimpsonCharModel>

    @GET("i/")
    fun getCharImage(@Url url: String): Call<IconModel>


    companion object {

        var BASE_URL = "http://api.duckduckgo.com/"
        var IMAGE_URL = "https://duckduckgo.com"

        fun createApi(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}