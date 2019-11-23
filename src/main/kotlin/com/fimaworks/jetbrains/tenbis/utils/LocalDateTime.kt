package com.fimaworks.jetbrains.tenbis.utils

import java.time.LocalDateTime
import java.time.ZoneOffset


/**
 * Check if @LocalDateTime is after a certain hour of the day
 */
fun LocalDateTime.isAfter(hour: Int, minutes: Int): Boolean {
    return this.isAfter(
        this
            .withHour(hour)
            .withMinute(minutes)
            .withSecond(0)
            .withNano(0)
    )
}

/**
 * is this @LocalDateTime is today
 */
fun LocalDateTime.isToday(): Boolean {

    val todayStart = LocalDateTime
        .now()
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)

    val todayEnd = todayStart.plusDays(1)

    val millis = this.millis

    if (millis >= todayStart.millis
        && millis < todayEnd.millis
    ) {
        return true
    }

    return false
}

/**
 * quick conversion to millis
 */
val LocalDateTime.millis: Long
    get() {
        return this.toInstant(ZoneOffset.UTC).toEpochMilli()
    }