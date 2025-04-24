package br.ufpa.genewhale.utils

import br.ufpa.genewhale.config.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import java.io.File

object Logger {
    suspend fun addLog(log: String) {
        return withContext(Dispatchers.IO) {
            val folder = Config.getDefaultFolderWithChild("logs")
            val file = File(folder, "log.txt")
            val now = Clock.System.now().toString()
            file.bufferedWriter().use {
                it.appendLine("$now: $log")
            }
        }
    }
}