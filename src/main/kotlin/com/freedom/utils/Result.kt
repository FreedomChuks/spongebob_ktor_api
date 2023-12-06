package com.freedom.utils

sealed interface ServiceResult<out T>{
    data class Success<out T>(val data: T) : ServiceResult<T>
    data class Error(val error: ErrorCode) : ServiceResult<Nothing>
}