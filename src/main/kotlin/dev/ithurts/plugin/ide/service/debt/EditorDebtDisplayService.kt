package dev.ithurts.plugin.ide.service.debt

import com.intellij.openapi.components.service
import com.intellij.openapi.editor.markup.MarkupModel
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import dev.ithurts.plugin.common.FileUtils
import dev.ithurts.plugin.ide.editor.DebtGutterIconRenderer
import dev.ithurts.plugin.ide.model.DebtStatus
import dev.ithurts.plugin.ide.model.DebtView
import dev.ithurts.plugin.ide.model.start

class EditorDebtDisplayService(private val project: Project) {
    fun renderDebtHighlighters() {
        renderDebtHighlighters(FileEditorManager.getInstance(project).allEditors)
    }

    fun renderDebtHighlighters(fileEditors: Array<FileEditor>) {
        fileEditors.forEach { fileEditor ->
            if (fileEditor !is TextEditor) {
                return@forEach
            }
            val file = fileEditor.file!!
            val debtsService = project.service<DebtStorageService>()
            val relativePath = FileUtils.getRelativePath(project, file)
            val debts = debtsService.getDebts(relativePath)

            val markupModel = fileEditor.editor.markupModel
            removeOldHighlighters(markupModel)

            if (debts.isEmpty()) return@forEach
            val debtGroupsByStartLine = debts.flatMap { debt ->
                debt.bindings.map { it.lines.start to debt}
            }.groupBy({ it.first }, { it.second })

            renderNewHighlighters(debtGroupsByStartLine, markupModel, relativePath)
        }
    }

    private fun renderNewHighlighters(
        debtGroupsByStartLine: Map<Int, List<DebtView>>,
        markupModel: MarkupModel,
        relativePath: String
    ) {
        debtGroupsByStartLine.forEach { (line, debts) ->
            val lineHighlighter = markupModel.addLineHighlighter(
                null, line - 1, 1
            )
            lineHighlighter.gutterIconRenderer =
                DebtGutterIconRenderer(
                    debts.size,
                    debts[0].title,
                    relativePath,
                    line,
                    debts.all { it.status != DebtStatus.OPEN })
        }
    }

    private fun removeOldHighlighters(markupModel: MarkupModel) {
        markupModel.allHighlighters.filter { highlighter ->
            highlighter.gutterIconRenderer is DebtGutterIconRenderer
        }.forEach { markupModel.removeHighlighter(it) }
    }
}