package br.ufpa.genewhale.config

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import javax.swing.filechooser.FileSystemView

/**
 * Config object to handle the configuration files
 * @see [Config] for more information
 */
object Config {
    /**
     * Get the default folder to save the config files
     * @return the default folder to save the config files
     */
    private fun getDefaultFolder(): File {
        val folder = File(FileSystemView.getFileSystemView().defaultDirectory, APP_FOLDER)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }

    /**
     * Get the default folder to save the config files with a child folder
     * @param folder the name of the child folder
     * @return the default folder to save the config files with a child folder
     */
    fun getDefaultFolderWithChild(folder: String): File {
        val file = File(getDefaultFolder(), folder)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    /**
     * Save a dataclass config
     * @param name the name of file to save
     * @param dataClass the dataclass to serializer, need annotation @Serializable
     */
    suspend inline fun <reified T> save(name: String, dataClass: T) {
        withContext(Dispatchers.IO) {
            val file = File(getDefaultFolderWithChild(CONFIG_FOLDER), name)
            if (!file.exists()) {
                file.createNewFile()
            }
            val data = Json.encodeToString(dataClass)
            file.bufferedWriter().use { writer ->
                writer.write(data)
            }
        }
    }

    /**
     * Load a dataclass config
     * @param name the name of config file
     * @return the dataclass or null if not exists
     */
    suspend inline fun <reified T> load(name: String): T? {
        return withContext(Dispatchers.IO) {
            val file = File(getDefaultFolderWithChild(CONFIG_FOLDER), name)

            if (!file.exists()) {
                return@withContext null
            }

            return@withContext try {
                val text = file.bufferedReader().use { it.readText() }
                Json.decodeFromString<T>(text)
            } catch (e: Exception) {
                null
            }
        }
    }

    const val PANAROO_CONFIG_FILE = "panaroo.json"
    const val APP_FOLDER = "genewhale"
    const val CONFIG_FOLDER = "config"
}