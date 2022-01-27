package dev.ithurts.plugin.ide.model

open class Binding(
    val filePath: String,
    val lines: Pair<Int, Int>,
    val advancedBinding: AdvancedBinding? = null
) {
    override fun toString(): String {
        if (advancedBinding != null) {
            return advancedBinding.toString()
        }
        val file = filePath.substringAfterLast("/")
        val lines = "${lines.first}${if (lines.first == lines.second) "" else "-${lines.second}"}"
        return "Source code $file:$lines"
    }
}