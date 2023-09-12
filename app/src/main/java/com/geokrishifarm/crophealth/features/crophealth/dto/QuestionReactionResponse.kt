package com.geokrishifarm.crophealth.features.crophealth.dto

import com.google.gson.annotations.SerializedName

data class QuestionReactionResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("parent")
    var parentId: Int? = null,
    @SerializedName("description")
    var description: String? = null,
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