package dev.ithurts.plugin.ide.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import dev.ithurts.plugin.client.ItHurtsClient
import dev.ithurts.plugin.common.RepoUtils
import dev.ithurts.plugin.ide.service.debt.DebtEditorDisplayService
import dev.ithurts.plugin.ide.service.debt.DebtStorageService

class FetchDebtsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val debtStorageService = project.service<DebtStorageService>()

        ItHurtsClient.getDebtsForRepo(
            RepoUtils.getRemote(project),
            {
                debtStorageService.indexDebts(it)
                ApplicationManager.getApplication().invokeLater {
                    project.service<DebtEditorDisplayService>().renderDebtHighlighters()
                }
            },
            { throw Exception(it.message) }
        )
    }
}