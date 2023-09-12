package com.geokrishifarm.crophealth.repo

import android.content.Context
import android.util.Log
import com.geokrishifarm.crophealth.features.crophealth.dto.CropHealthRequest
import com.geokrishifarm.crophealth.features.crophealth.dto.TagItem
import com.geokrishifarm.crophealth.framework.FLWResponse
import com.geokrishifarm.crophealth.retrofit.APIService
import com.geokrishifarm.crophealth.retrofit.NetworkModule
import com.geokrishifarm.crophealth.retrofit.ProgressRequestBody
import com.geokrishifarm.crophealth.retrofit.RetroHelper
import com.geokrishifarm.crophealth.utils.NetworkUtils
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class CropHealthRepo(val context: Context, val apiService: APIService) {

    val TAG = "CropHealthRepo"



    companion object{
        var repo : CropHealthRepo? =null
        fun provideCropHealthRepo(context: Context) : CropHealthRepo{
            return repo?: synchronized(this){
                repo?: CropHealthRepo(context , NetworkModule.getInstance().provideApiService(context)).also {
                    repo = it
                }
            }
        }

    }

    suspend fun sendPostCropHealthQuery(
        request: CropHealthRequest
    ) = flow<FLWResponse<String>> {

        val retroHelper = RetroHelper<String>()


        val arrayOfImages = ArrayList<MultipartBody.Part>()
        val arrayOfAudio = ArrayList<MultipartBody.Part>()


        if (!request.audio.isNullOrEmpty()) {
            for ((index, audioInfo) in request.audio.withIndex()) {
                arrayOfAudio.add(retroHelper.prepareFilePart("audio$index",
                    context,
                    "audio",
                    audioInfo.path!!,
                    audioInfo.fileType!!,
                    object : ProgressRequestBody.UploadCallbacks {
                        override fun onProgressUpdate(
                            individualUploadPercent: Double,
                            individualFileUploadedInMb: Double?,
                            individualFileTotalSizeInMb: Double?,
                            status: ProgressRequestBody.ProgressStatus?
                        ) {
                            when (status) {
                                ProgressRequestBody.ProgressStatus.FINISHED -> {
                                    Log.d(TAG,"audio uploaded")
                                    retroHelper.updateTotalUploadedFileCount()
                                    Log.d(TAG,"audio uploaded")

                                    retroHelper.totalFileUploadedInMb(
                                        "audio$index",
                                        individualFileUploadedInMb!!
                                    )





                                    /* todo receive in ui of crop health
                                         liveData.postValue(
                                        LDResponse.inProgress(
                                            setProgressStat(
                                                "Uploading ${audioInfo.path!!}",
                                                retroHelper
                                            )
                                        )
                                    )*/
                                }

                                ProgressRequestBody.ProgressStatus.IN_PROGRESS -> {
                                    Log.d(TAG,"$individualUploadPercent -> ${audioInfo.path!!} ")
                                    Log.d(TAG,"audio uploaded")
                                    retroHelper.totalFileUploadedInMb(
                                        "audio$index",
                                        individualFileUploadedInMb!!
                                    )
                                    /*todo receive in ui of crop health
                                       liveData.postValue(
                                        LDResponse.inProgress(
                                            setProgressStat(
                                                "Uploading ${audioInfo.path!!}",
                                                retroHelper
                                            )
                                        )
                                    )*/
                                }

                                ProgressRequestBody.ProgressStatus.ERROR -> {
                                    Log.e(TAG,"error uploading audio")
                                }

                                else -> {}
                            }
                        }

                    }

                ))
            }
        }


        if (request.images != null) {
            for ((index, imageInfo) in request.images.withIndex()) {
                arrayOfImages.add(retroHelper.prepareFilePart("image$index",
                    context,
                    "images",
                    imageInfo.path!!,
                    imageInfo.fileType!!,
                    object : ProgressRequestBody.UploadCallbacks {
                        override fun onProgressUpdate(
                            individualUploadPercent: Double,
                            individualFileUploadedInMb: Double?,
                            individualFileTotalSizeInMb: Double?,
                            status: ProgressRequestBody.ProgressStatus?
                        ) {
                            when (status) {
                                ProgressRequestBody.ProgressStatus.FINISHED -> {
                                    Log.d(TAG,"images uploaded")
                                    retroHelper.updateTotalUploadedFileCount()
                                    Log.d(TAG,"images uploaded")
                                    retroHelper.totalFileUploadedInMb(
                                        "image$index",
                                        individualFileUploadedInMb!!
                                    )
                                    /* todo receice is ui
                                    liveData.postValue(
                                        LDResponse.inProgress(
                                            setProgressStat(
                                                "Uploading ${imageInfo.path!!}",
                                                retroHelper
                                            )
                                        )
                                    )*/
                                }

                                ProgressRequestBody.ProgressStatus.IN_PROGRESS -> {
                                    Log.d(TAG,"$individualUploadPercent -> ${imageInfo.path!!} ")
                                    Log.d(TAG,"images uploaded")

                                    retroHelper.totalFileUploadedInMb(
                                        "image$index",
                                        individualFileUploadedInMb!!
                                    )
                                    /* todo received in ui
                                        liveData.postValue(
                                        LDResponse.inProgress(
                                            setProgressStat(
                                                "Uploading ${imageInfo.path!!}",
                                                retroHelper
                                            )
                                        )
                                    )*/
                                }

                                ProgressRequestBody.ProgressStatus.ERROR -> {
                                    Log.e(TAG, "image upload error")
                                }

                                else -> {}
                            }
                        }

                    }

                ))
            }
        }

        try {
            Log.d(TAG,"inside ch repo")
            if (!NetworkUtils.hasInternetConnection(context)) {
                emit(FLWResponse.noInternetAvailable())
                return@flow
            }

            Log.d(TAG,request.toString())
            retroHelper.enqueue(
                context,
                apiService.sendCropHealthRequest(request.tagType, request.tag,
                    request.description, request.liveStockAge,arrayOfImages, arrayOfAudio[0])
            ).collect {
                emit(it)
            }
        } catch (ex: Exception) {
            Log.d(TAG , ex.message.toString())
            emit(FLWResponse.onException(ex.message))
        }
    }


    suspend fun sendTagListRequest(tag : String) = flow {

        val retroHelper = RetroHelper<List<TagItem>>()

        try {
            Log.d(TAG,"inside ch repo")
            if (!NetworkUtils.hasInternetConnection(context)) {
                emit(FLWResponse.noInternetAvailable())
                return@flow
            }

            Log.d(TAG,tag.toString())
            retroHelper.enqueue(
                context,
                apiService.sendTagListRequest(tag)
            ).collect {
                emit(it)
            }
        } catch (ex: Exception) {
            Log.d(TAG , ex.message.toString())
            emit(FLWResponse.onException(ex.message))
        }
    }



}