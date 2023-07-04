package com.example.cybtest.api

import com.example.cybtest.modal.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicApi {

    @GET("movie")
    fun getMovie(@Query("query") name: String): Call<ApiResponse>
}