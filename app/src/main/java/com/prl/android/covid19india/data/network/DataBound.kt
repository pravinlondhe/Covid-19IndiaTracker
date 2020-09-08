package com.prl.android.covid19india.data.network

sealed class DataBound<out T>
class Loading<out T> : DataBound<T>()
data class Error<out T>(val errorCode: String?, val errorMsg: String?) : DataBound<T>()
data class Success<out T>(val data: T) : DataBound<T>()
