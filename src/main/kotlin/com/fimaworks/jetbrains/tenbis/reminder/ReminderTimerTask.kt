package com.fimaworks.jetbrains.tenbis.reminder

import com.fimaworks.jetbrains.tenbis.extensions.*
import com.fimaworks.jetbrains.tenbis.state.ReminderPersistentStateComponent
import java.time.LocalDateTime
import java.util.*

class ReminderTimerTask : TimerTask() {
    override fun run() {

        // get the persistent state
        val configState = ReminderPersistentStateComponent.instance.state

        // check the time
        val reminderHour = configState.reminderHour
        val reminderMinutes = configState.reminderMinutes

        // check if already reminded today
        val isRemindedForToday = configState.lastReminder.isToday()

        // and check that it's not too early to remind
        val isAfterReminderTime = LocalDateTime.now().isAfter(reminderHour, reminderMinutes)

        if (isRemindedForToday.not() && isAfterReminderTime) {

            // todo - add notification sound?

            // ui notification
            ReminderNotification.notifyUser()

        }
    }
}