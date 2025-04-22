package br.ufpa.genewhale.ui.viewmodels

import br.ufpa.genewhale.docker.PanarooService
import br.ufpa.genewhale.ui.states.GlobalEffect
import br.ufpa.genewhale.ui.states.GlobalState
import com.sun.management.OperatingSystemMXBean
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.management.ManagementFactory

class Global(
    private val panarooService: PanarooService
) {
    private val _uiState = MutableStateFlow(GlobalState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<GlobalEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    var job: Job? = null

    init {
        operatingSystemMemory()
    }

    suspend fun handleEffect(effect: GlobalEffect) {
        when (effect) {
            is GlobalEffect.ShowSnackBar -> {
                _uiEffect.emit(effect)
            }
        }
    }

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