package com.example.fetchapp.network

import com.example.fetchapp.model.FetchItem
import retrofit2.Response
import retrofit2.http.GET

interface FetchService {
    @GET("hiring.json")
    suspend fun getFetchItems(): Response<List<FetchItem>>
}
