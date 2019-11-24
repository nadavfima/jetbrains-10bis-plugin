package com.fimaworks.jetbrains.tenbis.state

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import java.time.LocalDateTime

// the @State annotation helps IntelliJ automatically serialize and save our state
@State(
    name = "ReminderPersistentStateComponent",
    storages = [Storage("10bis-plugin.xml")]
)
open class ReminderPersistentStateComponent : PersistentStateComponent<ReminderPersistentStateComponent.ReminderState> {

    // this is how we're going to call the component from different classes
    companion object {
        val instance: ReminderPersistentStateComponent
            get() = ServiceManager.getService(ReminderPersistentStateComponent::class.java)
    }

    // the component will always keep our state as a variable
    var reminderState: ReminderState = ReminderState()

    // just an obligatory override from PersistentStateComponent
    override fun getState(): ReminderState {
        return reminderState
    }

    // after automatically loading our save state,  we will keep reference to it
    override fun loadState(state: ReminderState) {
        reminderState = state
    }

    // the POKO class that always keeps our state
    class ReminderState {

        // should show the orange icon?
        var showToolbarIcon = true

        // reminders
        var showReminders = true
        var reminderHour = 11
        var reminderMinutes = 0

        // non-configurable
        var lastReminder: LocalDateTime = LocalDateTime.now().minusDays(1)
    }

}