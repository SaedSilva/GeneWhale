package br.ufpa.genewhale.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

object Logger {
    val fileManager: FileManager = FileManagerImplFileKit()

    suspend fun addLog(e: Throwable) = withContext(Dispatchers.IO) {
        val now = Clock.System.now().toString()
        val message = "$now: ${e.stackTraceToString()}\n"
        fileManager.writeLog(message)
    }

    const val LOG_FILE_NAME = "log.txt"
}