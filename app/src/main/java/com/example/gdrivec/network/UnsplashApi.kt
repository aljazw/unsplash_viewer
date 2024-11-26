package com.example.gdrivec.network

import com.example.gdrivec.components.UnsplashResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("client_id") clientId: String, // Use your API key here
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}



