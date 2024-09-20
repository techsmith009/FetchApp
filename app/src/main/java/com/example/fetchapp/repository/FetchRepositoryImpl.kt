package com.example.fetchapp.repository


import com.example.fetchapp.model.ServiceResult
import com.example.fetchapp.model.FetchItem
import com.example.fetchapp.network.RetrofitCallbackHandler
import com.example.fetchapp.network.getFetchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchRepositoryImpl() : FetchRepository {

    override suspend fun getFetchItem(): List<FetchItem>? {
        val result = RetrofitCallbackHandler.processCall {
                getFetchService().getFetchItems()
            }


        return when (result) {
            is ServiceResult.Success -> result.data
            else -> null
        }
    }

}
