package com.fimaworks.jetbrains.tenbis.reminder

import com.intellij.openapi.Disposable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.Configurable.NoScroll
import javax.swing.*


// todo - add to plugin.xml?
class ReminderConfigurable : Configurable, NoScroll, Disposable{

    override fun getDisplayName(): String = "10bis Plugin Configuration"


    override fun createComponent(): JComponent? {
        val dialogPanel = JPanel()
        dialogPanel.layout = BoxLayout(dialogPanel, BoxLayout.Y_AXIS)

        // todo - reminder time configuration

        return dialogPanel
    }

    override fun dispose() {
        // todo
    }

    override fun isModified(): Boolean {
        // todo
        return true
    }

    override fun apply() {
        // todo
    }

    override fun reset() {
        // todo
    }
}