package com.fimaworks.jetbrains.tenbis.reminder

import com.fimaworks.jetbrains.tenbis.Constants
import com.fimaworks.jetbrains.tenbis.state.ReminderPersistentStateComponent
import com.intellij.ide.BrowserUtil
import com.intellij.notification.*
import java.time.LocalDateTime

object ReminderNotification {

    // every notification must have a defined group, with an ID, Display Type and log flag
    private val group = NotificationGroup("10bis Plugin", NotificationDisplayType.STICKY_BALLOON, false)

    private val notificationListener = NotificationListener { notification, event ->
        // event could be null in some cases
        if (event != null) {
            if (event.url != null) {
                BrowserUtil.browse(event.url)
            }
        }

        // dismiss notification whenever clicked something
        notification?.expire()
    }

    fun notifyUser() {

        // prep title & message
        val title = "Did you remember to order food?"

        val content = escapeString(
            "<a href='${Constants.browserUrl}'>Order Now</a>" + // order button
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + // six spaces
                    "<a href='dismiss'>Dismiss</a>" // dismiss buton
        )!!

        // create notificaiton
        val notification = group.createNotification(title, content, NotificationType.WARNING, notificationListener)

        // whenever a notification is dismissed
        notification.whenExpired {
            // update last reminder to now
            ReminderPersistentStateComponent.instance.state.lastReminder = LocalDateTime.now()
        }

        // ping user, not need for project object
        notification.notify(null)
    }

    private fun escapeString(string: String?): String? {
        return if (string == null || !string.contains("\n")) {
            string
        } else string.replace("\n".toRegex(), "\n<br />")
    }
}