package br.ufpa.genewhale.global

import br.ufpa.genewhale.services.DockerService
import br.ufpa.genewhale.services.WebService
import br.ufpa.genewhale.utils.Desktop
import com.sun.management.OperatingSystemMXBean
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.management.ManagementFactory

const val APP_VERSION = "1.0.0"

class Global(
    private val dockerServices: List<DockerService>,
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

    fun handleIntent(intent: GlobalIntent) {
        when (intent) {
            is GlobalIntent.ShowLoading -> {
                _uiState.update { it.reduce(intent) }
            }

            is GlobalIntent.HideLoading -> {
                _uiState.update { it.reduce(intent) }
            }

            is GlobalIntent.CloseApplication -> {
                _uiState.update { it.reduce(intent) }
                stopAll()
            }

            is GlobalIntent.DisableClick -> {
                _uiState.update { it.reduce(intent) }
            }

            is GlobalIntent.PermitsClick -> {
                _uiState.update { it.reduce(intent) }
            }
        }
    }

    suspend fun handleEffect(effect: GlobalEffect) {
        _uiEffect.emit(effect)
    }

    fun stopAll() {
        scope.launch {
            val stoppingJobs = dockerServices.map { service ->
                async { service.stop() }
            }
            stoppingJobs.awaitAll()
            _uiEffect.emit(GlobalEffect.CloseApplication)
        }
    }

    fun testVersion() {
        scope.launch {
            val version = webService.getLatestVersion()
            if (version != null && version != APP_VERSION) {
                handleEffect(
                    GlobalEffect.ShowSnackBarWithAction(
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