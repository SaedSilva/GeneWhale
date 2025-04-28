package br.ufpa.genewhale.utils

/**
 * Interface to handle the file system
 * @see [FileManagerImplFileKit] for implementation
 */
interface FileManager {
    /**
     * Get the path to the files directory
     */
    fun getFilesDirectory(): String

    /**
     * Get the path to the cache directory
     */
    fun getCacheDirectory(): String

    /**
     * Get the path to the database directory
     */
    fun getDatabaseDirectory(): String

    /**
     * Get the path to the config directory
     */
    fun getConfigDirectory(): String

    /**
     * Read a file from the file system
     * @param filePath the path to the file
     * @return the content of the file
     */
    suspend fun readFile(filePath: String): String

    /**
     * Read a config file from the file system
     * @param config the name of the config file
     * @return the content of the config file
     */
    suspend fun readConfig(config: String): String?

    /**
     * Write a file to the file system
     * @param filePath the path to the file
     * @param content the content of the file
     */
    suspend fun writeFile(filePath: String, content: String)

    /**
     * Write a file to the file system
     * @param filePath the path to the file
     * @param content the content of the file
     */
    suspend fun writeFile(filePath: String, content: ByteArray) = Unit

    /**
     * Write a config file to the file system
     * @param config the name of the config file
     * @param content the content of the config file
     */
    suspend fun writeConfig(config: String, content: String)

    /**
     * Write a log file to the file system
     * @param content the content of the log file
     */
    suspend fun writeLog(content: String)

    /**
     * Append content to a file
     * @param filePath the path to the file
     * @param content the content to append
     */
    suspend fun appendToFile(filePath: String, content: String)

    /**
     * Append content to a file
     * @param filePath the path to the file
     * @param content the content to append
     */
    suspend fun appendToFile(filePath: String, content: ByteArray) = Unit
}