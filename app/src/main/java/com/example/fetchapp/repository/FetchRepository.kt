package com.example.fetchapp.repository

import com.example.fetchapp.model.FetchItem


interface FetchRepository {

    suspend fun getFetchItem() : List<FetchItem>?

     companion object {
         fun getFetchRepo()  = FetchRepositoryImpl()
     }
}
