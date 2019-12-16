package com.assignment.mbas

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import com.google.common.base.Preconditions.checkArgument


object MyDateUtils {
    fun convertMilliSecondsToDateFormat(milliSeconds: Long): String {


        // Create a DateFormatter object for displaying date in specified format. mmm ddth/ddrd
        val dateFormat = "MMM-dd hh:mm:ss a"
        val formatter = SimpleDateFormat(dateFormat, Locale.US)
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds


        val mnthFormat = SimpleDateFormat("MMM", Locale.US)
        val month = mnthFormat.format(calendar.time)

        val formatDayOfMonth = SimpleDateFormat("d", Locale.US)
        val day = Integer.parseInt(formatDayOfMonth.format(calendar.time))
        val daySuffix = getDayOfMonthSuffix(day)

        val timeFormat = SimpleDateFormat("hh:mm:ss a", Locale.US)
        val timeStr = timeFormat.format(calendar.time)


        //        return formatter.format(calendar.getTime());

        return "$month $day$daySuffix $timeStr"
    }

    fun getDateDiff(endTime: Long): Int {
        val fromDate: Date?
        val toDate: Date?

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        val hr = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val sec = calendar.get(Calendar.SECOND)
        calendar.set(year, month, day, hr, min, sec)
        fromDate = calendar.time
        ////////////////////
        calendar.timeInMillis = endTime
        toDate = calendar.time

        return if (fromDate == null || toDate == null) {
            0
        } else ((toDate.time - fromDate.time) / (1000 * 60 * 60 * 24)).toInt()


    }


    private fun getDayOfMonthSuffix(n: Int): String {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: $n")
        if (n >= 11 && n <= 13) {
            return "th"
        }
        when (n % 10) {
            1 -> return "st"
            2 -> return "nd"
            3 -> return "rd"
            else -> return "th"
        }
    }

    @Throws(ParseException::class)
    fun stringToDateFormat(strDate: String): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val date = sdf.parse(strDate)
        return convertMilliSecondsToDateFormat(date.time)
    }
}
