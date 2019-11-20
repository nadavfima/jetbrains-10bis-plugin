package com.fimaworks.jetbrains.tenbis.reminder

import com.fimaworks.jetbrains.tenbis.extensions.isAfter
import com.fimaworks.jetbrains.tenbis.extensions.isToday
import com.intellij.openapi.components.BaseComponent
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


class ReminderComponent : BaseComponent {

    // todo - need to save last reminder so we don't popup everytime the user launches IntelliJ
    private var lastReminder = LocalDateTime.now().minusDays(1)

    private val timer = Timer("10bis_reminder_timer")

    private val timerTask =
        ReminderTimerTask(object :
            LastReminderListener {
            override fun updateLastReminder(now: LocalDateTime) {
                this@ReminderComponent.lastReminder = now
            }

            override val lastReminder: LocalDateTime
                get() = this@ReminderComponent.lastReminder
        })

    override fun initComponent() {
        super.initComponent()

        // schedule task every 1 minute
        timer.schedule(
            timerTask,
            Date(System.currentTimeMillis()),
            TimeUnit.MINUTES.toMillis(1)
        )
    }

    override fun disposeComponent() {
        timerTask.cancel()
        timer.cancel()
        super.disposeComponent()
    }

    class ReminderTimerTask(private val lastReminderListener: LastReminderListener) : TimerTask() {
        override fun run() {

            val isRemindedForToday = lastReminderListener.lastReminder.isToday()

            // todo - make this configurable

            val reminderHour = 11
            val reminderMinutes = 0

            val isAfterReminderTime = LocalDateTime.now().isAfter(reminderHour, reminderMinutes)

            if (isRemindedForToday.not() && isAfterReminderTime) {

                // todo - add sound?

                // ui notification
                ReminderNotification.remindUser()

                // update last reminder to now
                lastReminderListener.updateLastReminder(LocalDateTime.now())
            }
        }
    }

    interface LastReminderListener {
        fun updateLastReminder(now: LocalDateTime)

        val lastReminder: LocalDateTime
    }
}
