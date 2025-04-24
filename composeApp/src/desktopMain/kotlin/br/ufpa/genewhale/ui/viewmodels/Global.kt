package br.ufpa.genewhale.ui.viewmodels

import br.ufpa.genewhale.docker.PanarooService
import br.ufpa.genewhale.ui.APP_VERSION
import br.ufpa.genewhale.ui.states.GlobalEffect
import br.ufpa.genewhale.ui.states.GlobalState
import br.ufpa.genewhale.utils.Desktop
import br.ufpa.genewhale.web.WebService
import com.sun.management.OperatingSystemMXBean
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.management.ManagementFactory

class Global(
    private val panarooService: PanarooService,
    private val webService: WebService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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
            is GlobalEffect.ShowSnackBarWithAction -> {
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

    fun testVersion() {
        scope.launch {
            val version = webService.getLatestVersion()
            if (version != null && version != APP_VERSION) {
                handleEffect(GlobalEffect.ShowSnackBarWithAction(
                    message = "New version available: $version",
                    actionLabel = "Update",
                    action = {
                        Desktop.openBrowser("https://github.com/saedsilva/genewhale/releases")
                    }
                ))
            }
        }
    }

    private fun operatingSystemMemory() {
        val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        val totalMemory = osBean.totalMemorySize
        val processors = osBean.availableProcessors
        _uiState.update { it.copy(memoryBytes = totalMemory, threads = processors) }
    }
}