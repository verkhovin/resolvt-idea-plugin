package dev.ithurts.plugin.common

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import dev.ithurts.plugin.ide.toolwindow.ReportDebtToolWindow
import dev.ithurts.plugin.ide.toolwindow.ReportDebtToolWindow2

object UiUtils {
    fun rerenderReportDebtToolWindow(project: Project) {
        val toolWindow = getReportDebtToolWindow(project)
        val contentManager = toolWindow.contentManager
        contentManager.removeAllContents(true)
        val reportDebtToolWindow = ReportDebtToolWindow2(project)
        contentManager.addContent(reportDebtToolWindow.getContent())
        toolWindow.activate(null)
    }

    fun getReportDebtToolWindow(project: Project) = ToolWindowManager.getInstance(project).getToolWindow("Report Technical Debt")!!
}