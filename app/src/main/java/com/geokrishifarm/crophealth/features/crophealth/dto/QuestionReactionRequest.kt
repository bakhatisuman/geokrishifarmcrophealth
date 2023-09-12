package com.geokrishifarm.crophealth.features.crophealth.dto


import com.google.gson.annotations.SerializedName

data class QuestionReactionRequest(
    @SerializedName("plant_query")
    val plantQuery: Int?,
    @SerializedName("reaction")
    val reaction: Int?
)