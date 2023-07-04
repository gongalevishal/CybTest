package com.example.cybtest.api

import android.util.Log
import com.example.cybtest.util.Constants.API_KEY
import com.example.cybtest.util.Constants.BASE_URL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private fun getRetrofit(): Retrofit {

        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            request = request.newBuilder().url(url).build()
            Log.d("TAG", request.toString())
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(clientInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: ComicApi = getRetrofit().create(ComicApi::class.java)
}