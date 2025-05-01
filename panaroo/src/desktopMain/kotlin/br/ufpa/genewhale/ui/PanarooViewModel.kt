package br.ufpa.genewhale.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufpa.genewhale.global.Global
import br.ufpa.genewhale.global.GlobalEffect
import br.ufpa.genewhale.global.GlobalIntent
import br.ufpa.genewhale.params.PanarooParamsConfig
import br.ufpa.genewhale.services.DockerService
import br.ufpa.genewhale.services.PanarooUsages
import br.ufpa.genewhale.utils.Config
import br.ufpa.genewhale.utils.Desktop
import br.ufpa.genewhale.utils.toMB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PanarooViewModel(
    private val service: DockerService,
    private val global: Global,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PanarooUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateConfig = MutableStateFlow(PanarooParams())
    val uiStateConfig = _uiStateConfig.asStateFlow()

    init {
        loadConfig()
        viewModelScope.launch {
            service.logs.collect { output ->
                _uiState.update { it.reduce(PanarooUiIntent.UpdateOutput(output)) }
            }
        }
        viewModelScope.launch {
            service.isRunning.collect { isRunning ->
                _uiState.update { it.copy(isRunning = isRunning) }
            }
        }
        viewModelScope.launch {
            global.uiState.collect { globalState ->
                _uiStateConfig.update {
                    it.copy(
                        maxMemory = globalState.memoryBytes.toMB(),
                        maxThreads = globalState.threads,
                        memorySlider = 1.0f / globalState.memoryBytes.toMB() * it.memory,
                        threadsSlider = 1.0f / globalState.threads * it.threads
                    )
                }
            }
        }
    }

    private fun loadConfig() {
        runBlocking {
            val panaroo = Config.load<PanarooParamsConfig>(Config.PANAROO_CONFIG_FILE)
            panaroo?.let { data ->
                _uiStateConfig.update { state ->
                    PanarooParams.fromEntity(data, state)
                }
            }
        }
    }

    fun handleIntent(intent: PanarooUiIntent) {
        when (intent) {
            is PanarooUiIntent.ClickPickFolder -> clickPickFolder()
            is PanarooUiIntent.ChangeInputFolder -> changeInputFolder(intent)
            is PanarooUiIntent.ClearInputFolder -> clearInputFolder(intent)
            is PanarooUiIntent.ChangeOutputFolder -> changeOutputFolder(intent)
            is PanarooUiIntent.ClearOutputFolder -> clearOutputFolder(intent)
            is PanarooUiIntent.ClearOutput -> clearOutput(intent)
            is PanarooUiIntent.RunPanaroo -> runPanaroo()
            is PanarooUiIntent.UpdateOutput -> updateOutput(intent)
            is PanarooUiIntent.CloseScreen -> closeScreen()
            is PanarooUiIntent.OpenDocs -> openDocs()
        }
    }

    private fun clickPickFolder() {
        global.handleIntent(GlobalIntent.DisableClick)
    }

    fun handleConfigIntent(intent: PanarooParamsIntent) {
        config(intent)
    }

    private fun openDocs() {
        Desktop.openBrowser("https://gthlab.au/panaroo/#/gettingstarted/quickstart")
    }

    private fun config(intent: PanarooParamsIntent) {
        if (intent is PanarooParamsIntent.Save) {
            viewModelScope.launch {
                Config.save(
                    name = Config.PANAROO_CONFIG_FILE,
                    dataClass = PanarooParamsConfig.fromState(_uiStateConfig.value)
                )
                global.handleEffect(GlobalEffect.ShowSnackBar("Configuration saved"))
            }
        }
        _uiStateConfig.update { it.reduce(intent) }
    }

    private fun closeScreen() {
        viewModelScope.launch {
            global.job?.cancel()
            global.job = null
        }
    }

    private fun updateOutput(intent: PanarooUiIntent.UpdateOutput) {
        _uiState.update { it.reduce(intent) }
    }

    private fun runPanaroo() {
        viewModelScope.launch {
            service.start(
                input = _uiState.value.inputFolder,
                output = _uiState.value.outputFolder,
                memory = if (_uiStateConfig.value.memory > 0L) _uiStateConfig.value.memory else null,
                parameters = createParams(_uiStateConfig.value),
                type = PanarooUsages.BasicUsage
            )
        }
    }

    private fun clearOutput(intent: PanarooUiIntent.ClearOutput) {
        _uiState.update { it.reduce(intent) }
    }

    private fun clearOutputFolder(intent: PanarooUiIntent.ClearOutputFolder) {
        _uiState.update { it.reduce(intent) }
    }

    private fun changeOutputFolder(intent: PanarooUiIntent.ChangeOutputFolder) {
        _uiState.update { it.reduce(intent) }
    }

    private fun changeInputFolder(intent: PanarooUiIntent.ChangeInputFolder) {
        _uiState.update { it.reduce(intent) }
    }

    private fun clearInputFolder(intent: PanarooUiIntent.ClearInputFolder) {
        _uiState.update { it.reduce(intent) }
    }

    private fun createParams(config: PanarooParams): List<String> {
        val original = PanarooParams()
        val params = mutableListOf<String>()
            .apply {
                if (config.threads > 0) {
                    add("--threads")
                    add(config.threads.toString())
                }
                if (config.cleanMode != CleanMode.NONE) {
                    add("--clean-mode")
                    add(config.cleanMode.toString())
                }
                if (config.removeInvalidGenes) {
                    add("--remove-invalid-genes")
                }
                if (config.threshold.isNotBlank() && config.threshold != original.threshold) {
                    add("--threshold")
                    add(config.threshold)
                }
                if (config.familyThreshold.isNotBlank() && config.familyThreshold != original.familyThreshold) {
                    add("--family_threshold")
                    add(config.familyThreshold)
                }
                if (config.lenDifPercent.isNotBlank() && config.lenDifPercent != original.lenDifPercent) {
                    add("--len_dif_percent")
                    add(config.lenDifPercent)
                }
                if (config.familyLenDifPercent.isNotBlank() && config.familyLenDifPercent != original.familyLenDifPercent) {
                    add("--family_len_dif_percent")
                    add(config.familyLenDifPercent)
                }
                if (config.mergeParalogs) {
                    add("--merge_paralogs")
                }
                if (config.searchRadius.isNotBlank() && config.searchRadius != original.searchRadius) {
                    add("--search_radius")
                    add(config.searchRadius)
                }
                if (config.refindPropMatch.isNotBlank() && config.refindPropMatch != original.refindPropMatch) {
                    add("--refind_prop_match")
                    add(config.refindPropMatch)
                }
                if (config.refindMode != RefindMode.NONE) {
                    add("--refind-mode")
                    add(config.refindMode.toString())
                }
            }
        return params
    }
}