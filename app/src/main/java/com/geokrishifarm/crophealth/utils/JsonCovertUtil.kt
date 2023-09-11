package com.geokrishifarm.crophealth.utils

import android.util.Log
import org.json.JSONObject

object JsonCovertUtil {

    fun convertResponseToString(json: String): String {
        val jsonObject = JSONObject(json)
        try {
            return jsonObject.getString("notifyUser")
        } catch (ex: Exception) {
            Log.d("JsonCovertUtil", ex.message.toString())
        }

        return ""
    }
}