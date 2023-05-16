package com.dnovaes.csgolive.common.utilities.extensions

import java.time.ZonedDateTime

const val TWO_DIGITS = "%02d"

fun ZonedDateTime.getSummaryMatchTime(): String {
    val formattedHour = String.format(TWO_DIGITS, hour)
    val formattedDayMonth = String.format(TWO_DIGITS, dayOfMonth)
    val formattedMonth = String.format(TWO_DIGITS, monthValue)
    val formattedMinute = String.format(TWO_DIGITS, minute)
    return "${formattedDayMonth}/${formattedMonth} ${formattedHour}:${formattedMinute}"
}