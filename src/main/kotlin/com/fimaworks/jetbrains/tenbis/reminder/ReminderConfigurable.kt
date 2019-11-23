package com.fimaworks.jetbrains.tenbis.reminder

import com.intellij.openapi.Disposable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.Configurable.NoScroll
import java.text.NumberFormat
import javax.swing.*
import javax.swing.text.NumberFormatter


class ReminderConfigurable : Configurable, NoScroll, Disposable {

    private val hourFormatter = NumberFormatter(NumberFormat.getIntegerInstance()).also {
        it.minimum = 0
        it.maximum = 23
        it.allowsInvalid = true
    }

    private val minutesFormatter = NumberFormatter(NumberFormat.getIntegerInstance()).also {
        it.minimum = 0
        it.maximum = 59
        it.allowsInvalid = true
    }
    private val configState
        get() = ReminderPersistentStateComponent.instance.state

    // ui
    private var hourField: JFormattedTextField? =
        JFormattedTextField(hourFormatter).also { it.text = configState.reminderHour.toString() }

    private var minutesField: JTextField? =
        JFormattedTextField(minutesFormatter).also { it.text = configState.reminderMinutes.toString() }

    override fun getDisplayName(): String = "10bis Plugin Configuration"

    override fun createComponent(): JComponent? {
        val dialogPanel = JPanel()

        val timeLabel = JLabel("Reminder Time")

        dialogPanel.add(timeLabel)
        dialogPanel.add(createTimeSetting())

        return dialogPanel
    }

    // todo - need to fix ui
    private fun createTimeSetting(): JPanel {

        val timePanel = JPanel()

        timePanel.add(hourField)
        timePanel.add(JLabel(" : ")) // 10:00 seperator
        timePanel.add(minutesField)

        return timePanel
    }

    override fun dispose() {
        hourField = null
        minutesField = null
    }

    override fun isModified(): Boolean {

        return configState.reminderHour != hourField!!.text.toIntOrNull()
                || configState.reminderMinutes != minutesField!!.text.toIntOrNull()
    }

    override fun apply() {
        hourField!!.text.toIntOrNull()?.let {
            if (it in 0..23)
                configState.reminderHour = it

        }

        minutesField!!.text.toIntOrNull()?.let {
            if (it in 0..59)
                configState.reminderMinutes = it
        }
    }

    override fun reset() {
        hourField!!.text = configState.reminderHour.toString()
        minutesField!!.text = configState.reminderMinutes.toString()
    }
}