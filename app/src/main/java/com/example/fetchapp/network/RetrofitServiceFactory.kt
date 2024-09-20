package com.example.fetchapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceFactory {

    fun <T> getProvider(client: OkHttpClient = OkHttpClient(),
                        serviceClass: Class<T>,
                        baseUrl: String = ""): T {

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(client)
                .build()

        return retrofit.create(serviceClass)
    }
}
