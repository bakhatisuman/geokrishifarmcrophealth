package com.geokrishifarm.crophealth.features.crophealth.dto

import com.google.gson.annotations.SerializedName

data class CropHealthRequest(

    @SerializedName("tag_type")
    val tagType: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("livestock_age")
    val liveStockAge: String?,
    @SerializedName("images")
    val images: ArrayList<FileInfo>?,
    @SerializedName("audio")
    val audio: ArrayList<FileInfo>?
)