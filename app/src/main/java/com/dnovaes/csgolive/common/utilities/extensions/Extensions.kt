package com.dnovaes.csgolive.common.utilities.extensions

import android.content.Context
import androidx.annotation.StringRes
import com.dnovaes.csgolive.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.IsoFields

const val TWO_DIGITS = "%02d"

fun ZonedDateTime.getSummaryMatchTime(): String {
    val formattedHour = String.format(TWO_DIGITS, hour)
    val formattedDayMonth = String.format(TWO_DIGITS, dayOfMonth)
    val formattedMonth = String.format(TWO_DIGITS, monthValue)
    val formattedMinute = String.format(TWO_DIGITS, minute)
    return "${formattedDayMonth}.${formattedMonth} ${formattedHour}:${formattedMinute}"
}

fun LocalDateTime.getTodaysSummaryTimeLabel(context: Context): CharSequence {
    val formattedHour = String.format(TWO_DIGITS, hour)
    val formattedMinute = String.format(TWO_DIGITS, minute)
    val today = context.getString(R.string.today).capitalize()
    return "$today, $formattedHour:$formattedMinute"
}

fun LocalDateTime.getWeekdaySummaryTimeLabel(context: Context): CharSequence {
    val formattedHour = String.format(TWO_DIGITS, hour)
    val formattedMinute = String.format(TWO_DIGITS, minute)
    dayOfWeek.getResourceString(context)?.let {
        val weekday = context.getString(it).substring(0..2)
        return "$weekday, $formattedHour:$formattedMinute"
    }
    val formattedDayMonth = String.format(TWO_DIGITS, dayOfMonth)
    val formattedMonth = String.format(TWO_DIGITS, monthValue)
    return "${formattedDayMonth}.${formattedMonth} ${formattedHour}:${formattedMinute}"
}

fun LocalDate.isSameWeek(date2: LocalDate): Boolean {
    val year1 = this.get(IsoFields.WEEK_BASED_YEAR)
    val week1 = this.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
    val year2 = date2.get(IsoFields.WEEK_BASED_YEAR)
    val week2 = date2.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
    return year1 == year2 && week1 == week2
}

@StringRes
fun DayOfWeek.getResourceString(context: Context): Int? {
    return when (name.lowercase()) {
        "monday" -> R.string.monday
        "tuesday" -> R.string.tuesday
        "wednesday" -> R.string.wednesday
        "thursday" -> R.string.thursday
        "friday" -> R.string.friday
        "saturday" -> R.string.saturday
        "sunday" -> R.string.sunday
        else -> null
    }
}