package com.fimaworks.jetbrains.tenbis.state

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import java.time.LocalDateTime

@State(
    name = "ReminderPersistentStateComponent",
    storages = [Storage("10bis-plugin.xml")]
)
open class ReminderPersistentStateComponent : PersistentStateComponent<ReminderPersistentStateComponent.ConfigurationState> {

    private var myState: ConfigurationState =
        ConfigurationState()

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
        val instance: ReminderPersistentStateComponent
            get() = ServiceManager.getService(ReminderPersistentStateComponent::class.java)
    }
}