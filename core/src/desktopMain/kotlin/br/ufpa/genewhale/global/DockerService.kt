package br.ufpa.genewhale.global

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface DockerService {
    val logs: SharedFlow<String>
    val isRunning: StateFlow<Boolean>
    var currentProcess: Process?

    suspend fun remove()
    suspend fun stop()
}