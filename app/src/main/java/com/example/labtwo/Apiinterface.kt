package com.example.labtwo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Apiinterface {
        @GET("photos") // Replace "endpoint" with your actual endpoint
        fun getImages():Call<ImagesResponse>
        @GET("photos/{id}") // Replace "endpoint" with your actual endpoint
        fun getSpecificPhoto(@Path("id") id: String):Call<ImagesResponseItem>
}