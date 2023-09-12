package com.geokrishifarm.crophealth.features.crophealth.dto


import com.google.gson.annotations.SerializedName

data class TagItem(
    @SerializedName("tag")
    var tag: String? = null,
    var isSelected: Boolean = false
)
