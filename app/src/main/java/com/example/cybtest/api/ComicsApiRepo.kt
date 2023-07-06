package com.example.cybtest.api

import androidx.compose.runtime.mutableStateOf
import com.example.cybtest.modal.ApiResponse
import com.example.cybtest.modal.ComicResult
import com.example.cybtest.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsApiRepo(private val api: ComicApi) {
    val movies = MutableStateFlow<NetworkResult<ApiResponse>>(NetworkResult.Initial())

    fun query(query: String) {
        movies.value = NetworkResult.Loading()
        api.getMovie(query)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            movies.value = NetworkResult.Success(it)
                        }
                    } else {
                        movies.value = NetworkResult.Error(response.message())
                    }

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    t.localizedMessage?.let {
                        movies.value = NetworkResult.Error(it)
                    }
                    t.printStackTrace()
                }

            })
    }
}