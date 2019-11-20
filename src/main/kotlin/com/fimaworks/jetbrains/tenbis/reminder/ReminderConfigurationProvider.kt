package com.fimaworks.jetbrains.tenbis.reminder

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import java.time.LocalDateTime

@State(
    name = "ReminderConfigurationProvider",
    storages = [Storage("10bis-plugin.xml")]
)
class ReminderConfigurationProvider : PersistentStateComponent<ReminderConfigurationProvider.ConfigurationState> {

    private var myState: ConfigurationState = ConfigurationState()

    override fun getState(): ConfigurationState {
        return myState
    }

    override fun loadState(state: ConfigurationState) {
        myState = state
    }

    class ConfigurationState {
        var lastReminder: LocalDateTime = LocalDateTime.now().minusDays(1)
        var reminderHour = 11
        var reminderMinutes = 0
    }

    companion object {
        val instance: ReminderConfigurationProvider
            get() = ServiceManager.getService(ReminderConfigurationProvider::class.java)
    }
}