package br.ufpa.pangenome.ui.viewmodels

import br.ufpa.pangenome.docker.PanarooService
import br.ufpa.pangenome.ui.states.GlobalState
import com.sun.management.OperatingSystemMXBean
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import java.lang.management.ManagementFactory

class Global(
    private val panarooService: PanarooService
) {
    private val _uiState = MutableStateFlow(GlobalState())
    val uiState = _uiState.asStateFlow()

    init {
        operatingSystemMemory()
        println(_uiState.value)
    }

    var job: Job? = null

    fun stopAll() {
        runBlocking {
            val panaroo = async { panarooService.stop() }
            panaroo.await()
        }
    }

    private fun operatingSystemMemory() {
        val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        val totalMemory = osBean.totalMemorySize
        val processors = osBean.availableProcessors
        _uiState.update { it.copy(memoryBytes = totalMemory, threads = processors) }
    }
}