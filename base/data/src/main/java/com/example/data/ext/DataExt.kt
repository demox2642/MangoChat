package com.example.data.ext

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

const val DATE_FORMAT = "yyyy-MM-dd"
const val FULL_DATE_FORMAT_WIDTH_ZONE = "yyyy-MM-dd HH:mm:ss"

fun String.toDate(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT))

fun String.toDataTime(): LocalDateTime {
    val odt = OffsetDateTime.parse(this)
    val loc = LocalDateTime.of(odt.year, odt.month, odt.dayOfMonth, odt.hour, odt.minute)

    Log.e("DateParse", "$loc")
    return loc
}
