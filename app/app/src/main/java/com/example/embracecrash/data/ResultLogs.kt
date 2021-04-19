package com.example.embracecrash.data

sealed class ResultLogs<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultLogs<T>()
    data class Error(val exception: Exception) : ResultLogs<Nothing>()
}