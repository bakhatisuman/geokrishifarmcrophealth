package com.geokrishifarm.crophealth.features.crophealth.dto

import java.io.Serializable

class FileInfo : Serializable {


    // var uri: String? = null
    var fileType: String? = null
    var path: String? = null
    var sizeInMb: Double = 0.0

    // var uri: Uri?=null
    var uriString: String? = null


    constructor(fileType: String?, path: String?, sizeInMb: Double = 0.0, imageMId: String? = "") {
        this.fileType = fileType
        this.path = path
        this.sizeInMb = sizeInMb
    }

    constructor()
}