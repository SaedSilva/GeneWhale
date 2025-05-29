package br.ufpa.genewhale.global

/**
 * Interface for web service operations.
 */
interface WebService {
    /**
     * Fetches the latest version of the application from the server.
     * @return the latest version as a String, or null if the version could not be retrieved.
     */
    suspend fun getLatestVersion(): String?
}