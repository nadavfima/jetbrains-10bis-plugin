package com.fimaworks.jetbrains.tenbis

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class OrderFoodAction : AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        // doesn't get any simpler than this
        BrowserUtil.browse(Constants.browserUrl)
    }

    // should the button be hidden after the order was made?
    override fun update(e: AnActionEvent) {
        super.update(e)
    }
}