package dev.ithurts.plugin.ide.service.binding

import com.intellij.lang.Language

enum class Language {
    JAVA, KOTLIN, PYTHON;

    companion object {
        fun from(language: Language): dev.ithurts.plugin.ide.service.binding.Language? {
            return when(language.id){
                "JAVA" -> JAVA
                "KOTLIN" -> KOTLIN
                "Python" -> PYTHON
                else -> null
            }
        }
    }
}