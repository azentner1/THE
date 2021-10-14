package com.the.app.common.data

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val error: ErrorEntity) : DataResult<Nothing>()
}