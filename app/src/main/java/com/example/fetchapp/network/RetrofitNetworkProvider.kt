package com.example.fetchapp.network

fun getFetchService() : FetchService =
    RetrofitServiceFactory.getProvider(
        serviceClass = FetchService::class.java,
        baseUrl = base_url
    )

const val base_url = "https://fetch-hiring.s3.amazonaws.com/"
