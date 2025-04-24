package br.ufpa.genewhale.utils

import br.ufpa.genewhale.config.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import java.io.File

object Logger {
    suspend fun addLog(e: Throwable) = withContext(Dispatchers.IO) {
        val folder = Config.getDefaultFolderWithChild("logs")
        val file = File(folder, "log.txt")

        if (!file.exists()) {
            file.createNewFile()
        }

        val now = Clock.System.now().toString()
        val message = "$now: ${e.stackTraceToString()}\n"

        file.appendText(message)
    }
}