package io.newm.core.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

private val minutesSecondsFormatter = SimpleDateFormat("m:ss").apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

fun Long.millisToMinutesSecondsString(): String = minutesSecondsFormatter.format(Date(this))

fun Long.secondsToMinutesSecondsString(): String = (this * 1000).millisToMinutesSecondsString()