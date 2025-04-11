package br.ufpa.utils

object TerminalParser {
    fun addLineToList(
        line: String,
        list: List<String>
    ): List<String> {
        val parts = line.split('\r')
        val finalPart = parts.lastOrNull() ?: line

        return if (parts.size > 1 && list.isNotEmpty()) {
            list.dropLast(1) + finalPart
        } else {
            list + finalPart
        }
    }
}