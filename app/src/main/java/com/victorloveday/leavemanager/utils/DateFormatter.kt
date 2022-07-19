package com.victorloveday.leavemanager.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateFormatter {

    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun getMonth(month: String) {
        when(month) {
            "01" -> {"Jan"}
            "02" -> {"Feb"}
            "03" -> {"March"}
            "04" -> {"April"}
            "05" -> {"May"}
            "06" -> {"June"}
            "07" -> {"Jul"}
            "08" -> {"Aug"}
            "09" -> {"Sept"}
            "10" -> {"Oct"}
            "11" -> {"Nov"}
            "12" -> {"Dec"}
        }
    }

    fun getFormattedTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if(!includeMillis) {
            return "${if(hours < 10) "0" else ""}$hours:" +
                    "${if(minutes < 10) "0" else ""}$minutes:" +
                    "${if(seconds < 10) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if(hours < 10) "0" else ""}$hours:" +
                "${if(minutes < 10) "0" else ""}$minutes:" +
                "${if(seconds < 10) "0" else ""}$seconds:" +
                "${if(milliseconds < 10) "0" else ""}$milliseconds"
    }

    private fun salutation() {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        var greet = ""

        if (timeOfDay in 0..11) {
            greet = "Good Morning"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greet = "Good Afternoon"
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greet = "Good Evening"
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greet = "Good Night"
        }
    }



}