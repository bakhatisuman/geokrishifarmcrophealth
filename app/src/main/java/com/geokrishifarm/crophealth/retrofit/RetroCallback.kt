package com.geokrishifarm.crophealth.retrofit

interface RetroCallback<T> {

    fun onLoading()
    fun onFinished()

    //    200...299
    fun onSuccess(code: Int, response: T?)

    //400...599
    fun onError(code: Int, message: String?)

    //pure exception timeout etc
    // void onException(String error);

    fun onHttpException(error: String?, errorBody: String?)

    fun onSocketTimeoutException(error: String?)

    fun onIOException(error: String?)

    fun onException(error: String?)

    fun onTokenExpiredOrInvalid(message: String?)

    // fun onUserUnverified(message: String?)

//    fun onInterruptedIOException(error: String)

}

