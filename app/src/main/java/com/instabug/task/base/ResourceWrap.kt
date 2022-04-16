package com.instabug.task.base

// A generic class that contains data and status about loading this data.
sealed class ResourceWrap<T> {
    data class Progress<T>(val loading: Boolean, val partialData: T? = null) : ResourceWrap<T>()
    data class Success<T>(val data: T) : ResourceWrap<T>()
    data class Failure<T>(val message: String) : ResourceWrap<T>()

    companion object {
        fun <T> loading(isLoading: Boolean = true, partialData: T? = null): ResourceWrap<T> =
            Progress(isLoading, partialData)

        fun <T> success(data: T): ResourceWrap<T> = Success(data)

        fun <T> failure(e: String): ResourceWrap<T> = Failure(e)
    }
}
