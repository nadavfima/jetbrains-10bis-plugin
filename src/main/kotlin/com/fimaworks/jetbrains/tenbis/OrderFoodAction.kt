package com.fimaworks.jetbrains.tenbis

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class OrderFoodAction : AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        // doesn't get any simpler than this
        BrowserUtil.browse("https://www.10bis.co.il/")
    }

}