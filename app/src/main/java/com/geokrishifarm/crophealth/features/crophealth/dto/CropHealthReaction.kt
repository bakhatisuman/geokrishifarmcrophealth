package com.geokrishifarm.crophealth.features.crophealth.dto


import com.google.gson.annotations.SerializedName

data class CropHealthReaction(
    @SerializedName("reaction")
    val reaction: Int,
    @SerializedName("plantquery_answer")
    val plantqueryAnswerID: Int

)