package br.ufpa.pangenome.config

import kotlinx.serialization.json.Json
import java.io.File
import javax.swing.filechooser.FileSystemView

object Config {
    private fun getDefaultFolder(): File {
        val folder = File(FileSystemView.getFileSystemView().defaultDirectory, "papangenome")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }

    fun getDefaultFolderWithChild(folder: String): File {
        val file  = File(getDefaultFolder(), folder)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    inline fun <reified T> save(name: String, dataClass: T) {
        val file = File(getDefaultFolderWithChild("config"), name)
        if (!file.exists()) {
            file.createNewFile()
        }
        val data = Json.encodeToString(dataClass)
        file.bufferedWriter().use { writer ->
            writer.write(data)
        }
    }

    inline fun<reified T> load(name: String): T? {
        val file = File(getDefaultFolderWithChild("config"), name)

        if (!file.exists()) {
            return null
        }

        return try {
            val text = file.bufferedReader().use { it.readText() }
            Json.decodeFromString<T>(text)
        } catch (e: Exception) {
            null
        }
    }
}