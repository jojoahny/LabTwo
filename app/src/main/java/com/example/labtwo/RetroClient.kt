package com.example.labtwo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroClient {
    val BASE_URL = "https://jsonplaceholder.typicode.com/"
    fun getRetrofitClients(): Apiinterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)
    }
}