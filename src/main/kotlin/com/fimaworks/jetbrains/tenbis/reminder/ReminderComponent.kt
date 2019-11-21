package com.fimaworks.jetbrains.tenbis.reminder

import com.fimaworks.jetbrains.tenbis.extensions.isAfter
import com.fimaworks.jetbrains.tenbis.extensions.isToday
import com.intellij.openapi.components.BaseComponent
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


class ReminderComponent : BaseComponent {

    private var lastReminder = ReminderConfigurationProvider.instance.state.lastReminder

    private val timer = Timer("10bis_reminder_timer")

    private val timerTask =
        ReminderTimerTask(object :
            LastReminderListener {
            override fun updateLastReminder(now: LocalDateTime) {
                this@ReminderComponent.lastReminder = now
                ReminderConfigurationProvider.instance.state.lastReminder = now

            }

            override val lastReminder: LocalDateTime
                get() = this@ReminderComponent.lastReminder
        })

    override fun initComponent() {
        super.initComponent()

        // delay first trigger by 15 seconds
        val initialDelayInMillis = TimeUnit.SECONDS.toMillis(15)

        // schedule task every 30 seconds
        val periodInMillis = TimeUnit.SECONDS.toMillis(30)

        timer.schedule(
            timerTask,
            Date(System.currentTimeMillis() + initialDelayInMillis),
            periodInMillis
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

            val configState = ReminderConfigurationProvider.instance.state

            val reminderHour = configState.reminderHour
            val reminderMinutes = configState.reminderMinutes

            val isAfterReminderTime = LocalDateTime.now().isAfter(reminderHour, reminderMinutes)

            if (isRemindedForToday.not() && isAfterReminderTime) {

                // todo - add notification sound?

                // ui notification
                ReminderNotification.notifyUser(lastReminderListener)

            }
        }
    }

    interface LastReminderListener {
        fun updateLastReminder(now: LocalDateTime)

        val lastReminder: LocalDateTime
    }
}
