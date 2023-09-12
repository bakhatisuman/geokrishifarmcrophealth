package com.geokrishifarm.crophealth.retrofit

import com.geokrishifarm.crophealth.features.crophealth.dto.TagItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface APIService {
    @Multipart
    @POST("plant/query/")
    suspend fun sendCropHealthRequest(
        @Part("tag_type") tagType: String,
        @Part("tag") tag: String,
        @Part("description") description: String,
        @Part("livestock_age") livestockAge: String?,
        @Part images: List<MultipartBody.Part>,
        @Part audio: MultipartBody.Part?

    ): Response<String>

    @GET("get/query/tags/")
    suspend fun sendTagListRequest(@Query("type") tag: String): Response<List<TagItem>>
}
