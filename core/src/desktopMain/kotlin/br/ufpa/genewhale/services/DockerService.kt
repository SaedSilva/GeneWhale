package br.ufpa.genewhale.services

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for managing Docker services.
 */
interface DockerService {
    /**
     * Logs from the Docker service.
     */
    val logs: SharedFlow<String>

    /**
     * Indicates whether the Docker service is currently running.
     */
    val isRunning: StateFlow<Boolean>

    /**
     * The current process of the Docker service.
     */
    var currentProcess: Process?


    /**
     * Starts the Docker container with the specified parameters.
     * This method is generic and can be used for different types of Docker containers.
     * It takes an input directory, output directory, memory limit, and additional parameters.
     * The type parameter T is used to specify the type of Docker container to start.
     * @param input The input directory for the Docker container.
     * @param output The output directory for the Docker container.
     * @param memory Optional memory limit in MB for the Docker container.
     * @param parameters Additional parameters for the Docker command.
     * @param type The type of Docker container to start.
     */
    suspend fun <T : Enum<T>> start(
        input: String,
        output: String,
        memory: Long? = null,
        parameters: List<String> = emptyList(),
        type: T
    )

    /**
     * remove the Docker container.
     */
    suspend fun remove()

    /**
     * Stop the Docker service.
     */
    suspend fun stop()
}