package br.ufpa.genewhale.utils

import io.github.vinceglb.filekit.*
import kotlinx.coroutines.runBlocking
import kotlinx.io.buffered
import kotlinx.io.writeString
import java.io.File

class FileManagerImplFileKit : FileManager {
    init {
        FileKit.init("genewhale")
    }

    override fun getFilesDirectory(): String {
        val fileKit = FileKit.filesDir
        if (fileKit.notExists()) {
            runBlocking {
                fileKit.createDirectories()
            }
        }
        return fileKit.absolutePath()
    }

    override fun getCacheDirectory(): String {
        val fileKit = FileKit.cacheDir
        if (fileKit.notExists()) {
            runBlocking {
                fileKit.createDirectories()
            }
        }
        return fileKit.absolutePath()
    }

    override fun getDatabaseDirectory(): String {
        val fileKit = FileKit.databasesDir
        if (fileKit.notExists()) {
            runBlocking {
                fileKit.createDirectories()
            }
        }
        return fileKit.absolutePath()
    }

    override fun getConfigDirectory(): String {
        val file = File(getFilesDirectory(), "config").absolutePath
        if (!File(file).exists()) {
            File(file).mkdirs()
        }
        return file
    }

    override suspend fun readFile(filePath: String): String {
        return PlatformFile(filePath).readString()
    }

    override suspend fun readConfig(config: String): String? {
        val file = PlatformFile(PlatformFile(getConfigDirectory()), config)
        return if (file.exists()) {
            file.readString()
        } else {
            null
        }
    }

    override suspend fun writeFile(filePath: String, content: String) {
        val file = PlatformFile(filePath)
        file.writeString(content)
    }

    override suspend fun writeConfig(config: String, content: String) {
        val file = PlatformFile(PlatformFile(getConfigDirectory()), config)
        file.writeString(content)
    }

    override suspend fun writeLog(content: String) {
        val file = PlatformFile(PlatformFile(getFilesDirectory()), Logger.LOG_FILE_NAME)
        file.sink(append = true).buffered().use { it.writeString(content) }
    }

    override suspend fun appendToFile(filePath: String, content: String) {
        val file = PlatformFile(filePath)
        file.sink(append = true).buffered().use { it.writeString(content) }
    }

    private fun PlatformFile.notExists(): Boolean {
        return !this.exists()
    }
}