package br.ufpa.genewhale.global

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
     * remove the Docker container.
     */
    suspend fun remove()

    /**
     * Stop the Docker service.
     */
    suspend fun stop()
}