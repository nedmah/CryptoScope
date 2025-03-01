package com.example.core.util

import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun mapExceptionToMessage(e: Throwable): String {
    Log.e("Error in repository/datasource", "Exception occurred: ", e)
    return when (e) {
        is retrofit2.HttpException -> when (e.code()) {
            400 -> "Bad request: invalid data"
            401 -> "Unauthorized: please log in"
            403 -> "Forbidden: access denied"
            404 -> "Not found: resource unavailable"
            429 -> "Too many requests: slow down"
            in 500..599 -> "Server error: try again later"
            else -> "Server error: ${e.code()}"
        }
        is NetworkException -> "No internet connection"
        is SocketTimeoutException -> "Request timed out"
        is java.io.IOException -> "Network error: check your connection"
        is org.json.JSONException -> "Failed to parse server response"
        is IllegalStateException -> "App error: ${e.message ?: "unexpected state"}"
        is android.database.sqlite.SQLiteException -> "Database error: try again"
        is java.net.UnknownHostException -> "Cannot reach server: check your internet"
        is java.net.ConnectException -> "Connection failed: server unavailable"
        else -> "Something went wrong: ${e.message ?: "Unknown error"}"
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun <T> Flow<T>.handleErrors(errorMapper: (Throwable) -> String = ::mapExceptionToMessage): Flow<Resource<T>> =
    flow {
        emit(Resource.Loading(true))
        try {
            collect { value ->
                emit(Resource.Success(value))
            }
            emit(Resource.Loading(false))
        } catch (e: Throwable) {
            emit(Resource.Error(message = errorMapper(e)))
            emit(Resource.Loading(false))
        }
    }