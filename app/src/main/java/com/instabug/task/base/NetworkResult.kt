package com.instabug.task.base

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val message: String) : NetworkResult<T>()
}