package com.example.cybtest.modal


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ComicResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)