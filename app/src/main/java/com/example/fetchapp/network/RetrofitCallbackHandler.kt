package com.example.fetchapp.network

import android.util.Log
import com.example.fetchapp.model.ServiceException
import com.example.fetchapp.model.ServiceResult
import com.example.fetchapp.utility.asSingleString
import com.example.fetchapp.utility.StringWrapper
import com.example.fetchapp.R
import com.example.fetchapp.model.NoConnectivityException


import retrofit2.Response
import java.util.logging.Logger

object RetrofitCallbackHandler {

    suspend fun <T> processCall(
        call: suspend () -> Response<T>
    ) : ServiceResult<T> {
        val genericMessage = R.string.generic_service_error_message.asSingleString()
        return try {

            val serviceCallback = call()
            val body = serviceCallback.body()
            if (serviceCallback.isSuccessful && body != null) {
                ServiceResult.Success(body)
            } else {
                processGenericExceptionMessage(
                    serviceCallback.message().orEmpty(),
                    serviceCallback.code().toString()
                )
                getGenericServiceError("${serviceCallback.code()}", genericMessage)
            }

        } catch (exception: Exception) {
            when (exception) {
                is NoConnectivityException -> {
                    processGenericExceptionMessage("No Connectivity", null)
                    getConnectivityServiceError()
                }
                else -> {
                    processGenericExceptionMessage(exception.localizedMessage ?: "", null)
                    getGenericServiceError(null, genericMessage)
                }
            }
        }
    }

    private fun getConnectivityServiceError(): ServiceResult.Error {
        val error = R.string.generic_connectivity_error_message.asSingleString()
        return ServiceResult.Error(ServiceException(null, error))
    }

    private fun getGenericServiceError(
        errorCode: String?,
        errorMessage: StringWrapper
    ): ServiceResult.Error {
        return ServiceResult.Error(ServiceException(errorCode, errorMessage))
    }

    private fun processGenericExceptionMessage(message: String, code: String?) {
        val codeMessage = if (code == null) "" else "with code: $code; "
        Log.e(RetrofitCallbackHandler::class.java.name, "processGenericExceptionMessage: $codeMessage$message" )
    }
}
