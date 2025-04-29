package br.ufpa.genewhale.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/**
 * Config object to handle the configuration files
 * @see [Config] for more information
 */
object Config {
    val fileManager: FileManager = FileManagerImplFileKit()

    /**
     * Save a dataclass config
     * @param name the name of file to save
     * @param dataClass the dataclass to serializer, need annotation @Serializable
     */
    suspend inline fun <reified T> save(name: String, dataClass: T) {
        withContext(Dispatchers.IO) {
            val data = Json.encodeToString(dataClass)
            fileManager.writeConfig(name, data)
        }
    }

    /**
     * Load a dataclass config
     * @param name the name of config file
     * @return the dataclass or null if not exists
     */
    suspend inline fun <reified T> load(name: String): T? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val text = fileManager.readConfig(name) ?: return@withContext null
                Json.decodeFromString<T>(text)
            } catch (e: Exception) {
                null
            }
        }
    }

    const val PANAROO_CONFIG_FILE = "panaroo.json"
}