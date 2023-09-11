package com.geokrishifarm.crophealth.retrofit
import android.content.Context
import android.util.Log
import com.geokrishifarm.crophealth.framework.FLWResponse
import com.geokrishifarm.crophealth.utils.JsonCovertUtil
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import java.io.File
//https://futurestud.io/tutorials/retrofit-2-how-to-upload-a-dynamic-amount-of-files-to-server
class RetroHelper<T> {
    companion object {
        val SERVER_ERROR_MESSAGE = "Server error"
    }

    var totalUploadedFileCount = 0
    var totalFileCount = 0
    var totalFileByte = 0.0
    var totalFileUploadedInMb = 0.0
    var totalFileInMb = 0.0
    var totalUploadPercent = 0.0
    var hashMapUploadFile = HashMap<String, Double>()



    suspend fun enqueue(
        context: Context,
        response: Response<T>,
        saveonDB: Boolean = false,
        filterNotifyUserJson: Boolean = false
    ) = flow<FLWResponse<T>> {
        try {
            Log.d("status code" , response.code().toString())
            if (response.isSuccessful || response.code() == 201) {
                if (saveonDB) {
                    emit(FLWResponse.saveonDB(response.body()!!))
                } else {
                    if (filterNotifyUserJson) {
                        //  Logger.log(response.body().toString())
                        emit(
                            FLWResponse.success(
                                JsonCovertUtil.convertResponseToString(
                                    response.body().toString()
                                ) as T
                            )
                        )
                    } else {
                        emit(FLWResponse.success(response.body()!!))
                    }
                }
            } else {
                val responseBody = response.errorBody()
                if (response.code() in 400..499) {
                    try {
                        val jsonObject = JSONObject(responseBody!!.string())
                        val message = jsonObject.getString("message")
                        //val message = removeDoubleQuotesForError(response) as String
                        if (response.code() == 401) {
                            emit(FLWResponse.tokenExpired(message))
                        } else {
                            emit(FLWResponse.error(message))
                        }
                    } catch (ex: Exception) {
                        Log.d("Exception" , ex.message.toString())
                        emit(FLWResponse.onException(ex.message))
                    }
                } else if (response.code() >= 500) {
                    // retroCallback.onException("Server Error!")
                    // emit(FLWResponse.onException(responseBody!!.string()))
                    emit(FLWResponse.error("Server error"))
//                    Log.d("" + response.code() + ": " + responseBody!!.string())
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
//            Logger.log("retrohelper exception " + ex.message)
//            Logger.log("retrohelper exception " + response.toString())
            emit(FLWResponse.onException("Server error!"))
        }
    }


    /*fun enqueue(call: Call<T>, retroCallback: RetroCallback<T>) {


        retroCallback.onLoading()

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
//                Timber.v("status code" + response.code())
                if (response.isSuccessful) {
                    if (response.body() is String) {
                        retroCallback.onSuccess(response.code(), removeDoubleQuotes(response) as T)
                    } else {
                        retroCallback.onSuccess(response.code(), response.body())
                    }
                } else {
                    var responseBody = response.errorBody()
                    if (response.code() in 400..499) {
                        try {
                            var jsonObject = JSONObject(responseBody!!.string())
                            var message = jsonObject.getString("Message")

                            // var message = removeDoubleQuotesForError(response) as String
                            if (response.code() == 401) {
                                //unsubscribe notification from firebase...
                                retroCallback.onTokenExpiredOrInvalid(message)
                            } else {
                                retroCallback.onError(response.code(), message)
                            }
                        } catch (ex: Exception) {
//                            Timber.v(ex.message)
                        }
                    } else if (response.code() >= 500) {
                        retroCallback.onIOException("An error has occured !")
//                        Timber.v("" + response.code() + ": " + responseBody!!.string())
                    }
                }
                retroCallback.onFinished()
            }


            override fun onFailure(call: Call<T>, e: Throwable) {
//                Timber.v("Error on Failure" + e.message)
                // there is more than just a failing request (like: no internet connection)
                if (e is HttpException) {
                    val responseBody = e.response()?.errorBody()
                    try {
                        val response = responseBody!!.string()
//                        Timber.v(response)
                        val jsonObject = JSONObject(response)
                        retroCallback.onHttpException(jsonObject.getString("message"), response)
                    } catch (ex: Exception) {
//                        Timber.v(ex.message)
                    }

                } else if (e is SocketTimeoutException) {
                    retroCallback.onSocketTimeoutException("Connection Time Out!")
//                    retroCallback.onSocketTimeoutException(e.message)
                } else if (e is IOException) {
                    retroCallback.onIOException("An error has occured !")
                    //retroCallback.onIOException(e.message)
                } else if (e is Exception) {
//                    Timber.v("********* ********* ")
//                    Timber.v("********* " + e.message + " ********* ")
//                    Timber.v("********* ********* ")
                }

                retroCallback.onFinished()

            }
        })
    }*/


    /*
    JAVA DOC
     @param      beginIndex   the beginning index, inclusive.
    * @param      endIndex     the ending index, exclusive.
    * @return     the specified substring.
    * @exception  IndexOutOfBoundsException  if the
    *             {@code beginIndex} is negative, or
    *             {@code endIndex} is larger than the length of
    *             this {@code String} object, or
    *             {@code beginIndex} is larger than
    *             {@code endIndex}.
    */
    fun removeDoubleQuotes(response: Response<T>): String? {
//        Timber.v("" + response.body())
        if (response.body() == null) {
            return null
        }

        var result = response.body().toString()
        var resultWithoutDoubleQuotes = ""
        if (result.startsWith("\"")) {
            resultWithoutDoubleQuotes = result.substring(1, result.length - 1)
            return resultWithoutDoubleQuotes
        }
        return result
    }


    fun removeDoubleQuotesForError(response: Response<T>): String? {
//        Timber.v("" + response.errorBody())
        if (response.errorBody() == null) {
            return null
        }

        var result = response.errorBody()!!.string()
        var resultWithoutDoubleQuotes = ""
        if (result.startsWith("\"")) {
            resultWithoutDoubleQuotes = result.substring(1, result.length - 1)
            return resultWithoutDoubleQuotes
        }
        return result
    }


    // https://futurestud.io/tutorials/retrofit-2-how-to-upload-a-dynamic-amount-of-files-to-server
    fun prepareFilePart(
        key: String,
        context: Context,
        partName: String,
        path: String,
        fileType: String,
        uploadCallbacks: ProgressRequestBody.UploadCallbacks?
    ): MultipartBody.Part {
        val file = File(path)


        val progressRequestBody = ProgressRequestBody(
            file,
            fileType.toMediaTypeOrNull().toString(),
            uploadCallbacks
        )

        totalFileCount = totalFileCount + 1
        totalFileByte = totalFileByte + file.length()

        hashMapUploadFile.put(key, 0.0)


        totalFileInMb = (totalFileByte / (1024 * 1024))


        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), progressRequestBody)
    }


    fun totalFileUploadedInMb(key: String, mb: Double) {
        hashMapUploadFile.put(key, mb)
        totalFileUploadedInMb = 0.0
        for ((key, value) in hashMapUploadFile) {
            totalFileUploadedInMb = totalFileUploadedInMb + value
        }

        totalUploadPercent = (totalFileUploadedInMb / totalFileInMb) * 100
    }

    fun updateTotalUploadedFileCount() {
        totalUploadedFileCount++
    }

    fun createPartFromString(text: String): RequestBody {
        return RequestBody.create(
            okhttp3.MultipartBody.FORM, text
        )
    }


}

