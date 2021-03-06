package com.fimaworks.jetbrains.tenbis.state

import com.intellij.openapi.Disposable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.Configurable.NoScroll
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.text.NumberFormat
import javax.swing.*
import javax.swing.text.NumberFormatter


class ReminderPersistentStateConfigurable : Configurable, NoScroll, Disposable {

    // configurations for the text fields
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
    
    // this is the persistent component we can read or write to
    private val configState
        get() = ReminderPersistentStateComponent.instance.state

    // ui components
    private var showToolbarCheckbox: JCheckBox? = JCheckBox("Show Toolbar Button")
    private var showRemindersCheckbox: JCheckBox? = JCheckBox("Show Reminder Notifications")

    private var hourField: JFormattedTextField? =
        JFormattedTextField(hourFormatter).also { it.text = configState.reminderHour.toString() }

    private var minutesField: JTextField? =
        JFormattedTextField(minutesFormatter).also { it.text = configState.reminderMinutes.toString() }

    // providing a title
    override fun getDisplayName(): String = "10bis Plugin Configuration"

    // creating the ui
    override fun createComponent(): JComponent? {


        val formPanel = FormBuilder.createFormBuilder()
                // show toolbar button checkbox
            .addComponent(JPanel(FlowLayout(FlowLayout.LEFT)).also { it.add(showToolbarCheckbox) })
                // show reminder checkbox
            .addComponent(JPanel(FlowLayout(FlowLayout.LEFT)).also { it.add(showRemindersCheckbox) })
                // reminder time setting
            .addLabeledComponent("Daily Reminder Time", JPanel(FlowLayout(FlowLayout.LEFT)).also {
                it.add(hourField)
                it.add(JLabel(" : ")) // 10:00 seperator
                it.add(minutesField)
            })
            .panel

        return JPanel(BorderLayout()).also { it.add(formPanel, BorderLayout.NORTH) }
    }


    override fun dispose() {
        hourField = null
        minutesField = null
        showToolbarCheckbox = null
        showRemindersCheckbox = null
    }

    // this tells the preferences window whether to enable or disable the "Apply" button.
    // so if the user has changed anything - we want to know.
    override fun isModified(): Boolean {

        return configState.reminderHour != hourField!!.text.toIntOrNull()
                || configState.reminderMinutes != minutesField!!.text.toIntOrNull()
                || configState.showToolbarIcon != showToolbarCheckbox!!.isSelected
                || configState.showReminders != showRemindersCheckbox!!.isSelected
    }

    // when the user hits "ok" or "apply" we want o update the configurable state
    override fun apply() {
        hourField!!.text.toIntOrNull()?.let {
            if (it in 0..23)
                configState.reminderHour = it

        }

        minutesField!!.text.toIntOrNull()?.let {
            if (it in 0..59)
                configState.reminderMinutes = it
        }
        configState.showToolbarIcon = showToolbarCheckbox!!.isSelected
        configState.showReminders = showRemindersCheckbox!!.isSelected
    }

    // hitting "reset" shold reset the ui to the latest saved config
    override fun reset() {
        hourField!!.text = configState.reminderHour.toString()
        minutesField!!.text = configState.reminderMinutes.toString()
        showToolbarCheckbox!!.isSelected = configState.showToolbarIcon
        showRemindersCheckbox!!.isSelected = configState.showReminders
    }
}
