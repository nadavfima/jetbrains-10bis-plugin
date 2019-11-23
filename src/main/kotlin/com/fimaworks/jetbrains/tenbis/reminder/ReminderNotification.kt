package com.fimaworks.jetbrains.tenbis.reminder

import com.fimaworks.jetbrains.tenbis.Constants
import com.fimaworks.jetbrains.tenbis.state.ReminderPersistentStateComponent
import com.intellij.ide.BrowserUtil
import com.intellij.notification.*
import java.time.LocalDateTime

object ReminderNotification {

    fun notifyUser() {
        // prep message
        val message = escapeString(
            // order button
            "<a href='${Constants.browserUrl}'>Order Now</a>" +
                    // six spaces
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    // dismiss buton
                    "<a href='dismiss'>Dismiss</a>"
        )!!

        // create notificaiton
        val notification = NotificationGroup("10bis Plugin", NotificationDisplayType.STICKY_BALLOON, false)
            .createNotification(
                "Did you remember to order food?",
                message,
                NotificationType.WARNING
            ) { notification, event ->
                if (event != null) {
                    if (event.url != null) {
                        BrowserUtil.browse(event.url)
                    }
                }

                // dismiss notification
                notification?.expire()

            }

        notification.whenExpired {
            // update last reminder to now
            ReminderPersistentStateComponent.instance.state.lastReminder = LocalDateTime.now()
        }

        // ping user
        notification.notify(null)
    }

    private fun escapeString(string: String?): String? {
        return if (string == null || !string.contains("\n")) {
            string
        } else string.replace("\n".toRegex(), "\n<br />")
    }
}