package com.geokrishifarm.crophealth.features.crophealth.dto

import com.google.gson.annotations.SerializedName

data class CropHealthExt(
    @SerializedName("answers")
    var answers: List<Answer>? = null,
    @SerializedName("date_uploaded")
    var dateUploaded: String? = null,
    @SerializedName("date_uploaded_nepali")
    var dateUploadedNepali: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("livestock_age")
    var livestockAge: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("images")
    var images: List<String>? = null,
    @SerializedName("audio")
    var audio: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("status_icon")
    var statusIcon: String? = null,
    @SerializedName("title")
    var title: String? = null
) {
    data class Answer(
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("parent")
        var parentId: Int? = null,
        @SerializedName("answer")
        var answer: String? = null,
        @SerializedName("self")
        var isSelf: Boolean = false,
        @SerializedName("reaction")
        var reaction: Int? = null,
        @SerializedName("parent_answer")
        var parentAnswer: String? = null,
        @SerializedName("created_on")
        var createdOn: String? = null,
        @SerializedName("created_on_nepali")
        var createdOnNepali: String? = null,
        @SerializedName("full_name")
        var extFullName: String? = null,
        @SerializedName("image")
        var extImage: String? = null
    )
}