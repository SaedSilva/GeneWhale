package br.ufpa.genewhale.utils

interface FileManager {
    fun getFilesDirectory(): String
    fun getCacheDirectory(): String
    fun getDatabaseDirectory(): String
    fun getConfigDirectory(): String
    suspend fun readFile(filePath: String): String
    suspend fun readConfig(config: String): String?
    suspend fun writeFile(filePath: String, content: String)
    suspend fun writeFile(filePath: String, content: ByteArray) = Unit
    suspend fun writeConfig(config: String, content: String)
    suspend fun writeLog(content: String)
    suspend fun appendToFile(filePath: String, content: String)
    suspend fun appendToFile(filePath: String, content: ByteArray) = Unit
}