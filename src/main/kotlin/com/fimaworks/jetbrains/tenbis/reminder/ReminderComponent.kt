package com.fimaworks.jetbrains.tenbis.reminder

import com.intellij.openapi.components.BaseComponent
import java.util.*
import java.util.concurrent.TimeUnit

// in the plugin.xml we'll make sure that this component runs on application startup
class ReminderComponent : BaseComponent {

    // create a Timer and a TimerTask
    private val timer = Timer("10bis_reminder_timer")

    private val timerTask = ReminderTimerTask()

    override fun initComponent() {
        super.initComponent()

        // delay first trigger by 15 seconds
        val initialDelayInMillis = TimeUnit.SECONDS.toMillis(15)

        // schedule task every 30 seconds
        val periodInMillis = TimeUnit.SECONDS.toMillis(30)

        // schedule when the component is initiated
        timer.schedule(
            timerTask,
            Date(System.currentTimeMillis() + initialDelayInMillis),
            periodInMillis
        )
    }

    override fun disposeComponent() {
        // cancel the time when disposed
        timerTask.cancel()
        timer.cancel()
        super.disposeComponent()
    }
}
