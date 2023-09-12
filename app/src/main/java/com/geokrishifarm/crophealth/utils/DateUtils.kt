package com.geokrishifarm.crophealth.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtils {

    companion object {

        fun getFormattedDate(): String? {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            return simpleDateFormat.format(Calendar.getInstance().time)
        }



        fun getFormattedDateAndTime(): String? {
//            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(Calendar.getInstance().time)
        }

        fun getFormattedDateAndTimeForToken(): String? {
//            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            return format.format(Calendar.getInstance().time)
        }

        fun getTodayDate(format: String): String {
            val simpleDateFormat = SimpleDateFormat(format)
            return simpleDateFormat.format(Calendar.getInstance().time)
        }


        fun addDay(oldDate: String?, numberOfDays: Int): String? {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val c = Calendar.getInstance()
            try {
                c.time = dateFormat.parse(oldDate!!)!!
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            c.add(Calendar.DAY_OF_YEAR, numberOfDays)
            dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val newDate = Date(c.timeInMillis)
            return dateFormat.format(newDate)
        }


        fun subtractDayFromDate(oldDate: String?, numberOfDays: Int): String? {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val c = Calendar.getInstance()
            try {
                c.time = dateFormat.parse(oldDate!!)!!
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            c.add(Calendar.DAY_OF_YEAR, -numberOfDays)
            dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val newDate = Date(c.timeInMillis)
            return dateFormat.format(newDate)
        }

        fun dateTimeFormat(date: String): String {
            var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            format = SimpleDateFormat("dd MMM, yyyy")
            return format.format(newDate)
        }

        fun dateTimeFormatForDynamic(date: String): String {
            var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss a")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            format = SimpleDateFormat("MMM dd, yyyy")
            return format.format(newDate)
        }


        fun dateFormatEnglish(date: String): String {
            var format = SimpleDateFormat("yyyy-MM-dd")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            format = SimpleDateFormat("dd MMM, yyyy")
            return format.format(newDate)
        }


        fun dateEnglishShortMonth(date: String): String {
            var format = SimpleDateFormat("yyyy-MM-dd")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            format = SimpleDateFormat("dd-MM-yyyy")
            return format.format(newDate)
        }


        fun dateFormat(date: String): String {
            var format = SimpleDateFormat("MM/dd/yy")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            format = SimpleDateFormat("MMMM dd, yyyy")
            return format.format(newDate)
        }


        fun convertToNepaliDate(date: String): String {
            var format = SimpleDateFormat("dd-MM-yyyy")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("yyyy-MM-dd")
//            format = SimpleDateFormat("dd-MM-yyyy")
            return format.format(newDate)
        }


        fun currentDate(): String {

            val calender = Calendar.getInstance()
            val cDay = calender.get(Calendar.DAY_OF_MONTH).toString()
            val cMonth = (calender.get(Calendar.MONTH) + 1).toString()
            val cYear = calender.get(Calendar.YEAR).toString()
            val currentDate = "$cYear-$cMonth-$cDay"

            return currentDate
        }


        fun currentDateAndTime(): String {

            val calender = Calendar.getInstance()
            val cDay = calender.get(Calendar.DAY_OF_MONTH).toString()
            val cMonth = (calender.get(Calendar.MONTH) + 1).toString()
            val cYear = calender.get(Calendar.YEAR).toString()
            val cHour = calender.get(Calendar.HOUR_OF_DAY).toString()
            val cMinute = calender.get(Calendar.MINUTE).toString()
            val cSecond = calender.get(Calendar.SECOND).toString()
            val cMilliSecond = calender.get(Calendar.MILLISECOND).toString()
//            2018-11-13 16:43:53.457800
            return "$cYear-$cMonth-$cDay $cHour:$cMinute:$cSecond.$cMilliSecond"
        }

        fun currentNepaliDate(): String {

            val calender = Calendar.getInstance()
            val cDay = calender.get(Calendar.DAY_OF_MONTH).toString()
            val cMonth = (calender.get(Calendar.MONTH) + 1).toString()
            val cYear = calender.get(Calendar.YEAR).toString()

            val currentNepaliDate = cDay + "-" + cMonth + "-" + cYear

            return currentNepaliDate
        }

        fun subtractDate(moreValue: String, lessValue: String): Long { //milliseconds

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            var different = 0L
            try {
                val date1 = simpleDateFormat.parse(moreValue)
                val date2 = simpleDateFormat.parse(lessValue)

                Log.v("Job Date1 : " ,   "$date1 Date 2")

                different = date1!!.time - date2!!.time
//                numberOfDay = different
//                different  = different/1000*60*60*24
                /*val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24
                val elapsedDays = different / daysInMilli
                different = different % daysInMilli
                val elapsedHours = different / hoursInMilli
                different = different % hoursInMilli
                val elapsedMinutes = different / minutesInMilli
                different = different % minutesInMilli
                val elapsedSeconds = different / secondsInMilli*/


            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return different
        }


        fun changeDateToMilliSecond(moreValue: String): Long? { //milliseconds

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            var different = 0L
            try {
                val date1 = simpleDateFormat.parse(moreValue)
                different = date1!!.time

            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return different
        }


        // this method is use for server .
        fun currentNepaliDateFormat(): String {

            val calender = Calendar.getInstance()
            val cDay = calender.get(Calendar.DAY_OF_MONTH).toString()
            val cMonth = (calender.get(Calendar.MONTH) + 1).toString()
            val cYear = calender.get(Calendar.YEAR).toString()

            val currentNepaliDateFormat = cYear + "-" + cMonth + "-" + cDay

            return currentNepaliDateFormat
        }

        fun getDayFromDate(date: String): Int {
            var format = SimpleDateFormat("dd-MM-yyyy")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("dd")
            return format.format(newDate).toInt()
        }


        fun getMonthFromDate(date: String): Int {
            var format = SimpleDateFormat("dd-MM-yyyy")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("MM")
            return format.format(newDate).toInt()
        }

        fun getYearFromDate(date: String): Int {
            var format = SimpleDateFormat("dd-MM-yyyy")
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("yyyy")
            return format.format(newDate).toInt()
        }

        fun getMonthFromNepaliDate(nepaliDate: String): Int {
            //2077-08-04
            return nepaliDate.split("-")[1].toInt()
        }

        fun splitNepaliDate(nepaliDate: String):List<String>{
            return nepaliDate.split("-")
        }

        fun getDayFromNepaliDate(nepaliDate: String): Int {
            //2077-08-04
            return nepaliDate.split("-")[2].toInt()
        }





    }


}