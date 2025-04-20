package br.ufpa.pangenome.ui.viewmodels.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufpa.pangenome.config.Config
import br.ufpa.pangenome.config.params.PanarooParamsConfig
import br.ufpa.pangenome.docker.PanarooService
import br.ufpa.pangenome.ui.states.GlobalEffect
import br.ufpa.pangenome.ui.states.tools.*
import br.ufpa.pangenome.ui.viewmodels.Global
import br.ufpa.pangenome.utils.Desktop
import br.ufpa.pangenome.utils.toMB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PanarooViewModel(
    private val service: PanarooService,
    private val global: Global,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PanarooUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateConfig = MutableStateFlow(PanarooParams())
    val uiStateConfig = _uiStateConfig.asStateFlow()

    init {
        runBlocking {
            val data = Config.load<br.ufpa.pangenome.config.params.PanarooParamsConfig>("panaroo.json")
            data?.let {
                _uiStateConfig.update { state ->
                    PanarooParams.fromEntity(it, state)
                }
            }
        }


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
                        threadsSlider = 1.0f / globalState.threads * it.threads
                    )
                }
            }
        }
    }

    fun handleIntent(intent: PanarooUiIntent) {
        when (intent) {
            is PanarooUiIntent.ChangeInputFolder -> changeInputFolder(intent)
            is PanarooUiIntent.ClearInputFolder -> clearInputFolder(intent)
            is PanarooUiIntent.ChangeOutputFolder -> changeOutputFolder(intent)
            is PanarooUiIntent.ClearOutputFolder -> clearOutputFolder(intent)
            is PanarooUiIntent.ClearOutput -> clearOutput(intent)
            is PanarooUiIntent.RunPanaroo -> runPanaroo(intent)
            is PanarooUiIntent.UpdateOutput -> updateOutput(intent)
            is PanarooUiIntent.CloseScreen -> closeScreen(intent)
            is PanarooUiIntent.OpenDocs -> openDocs()
        }
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
                Config.save(Config.DEFAULT_PANAROO_CONFIG, PanarooParamsConfig.fromState(_uiStateConfig.value))
                global.handleEffect(GlobalEffect.ShowSnackBar("Configuration saved"))
            }
        }
        _uiStateConfig.update { it.reduce(intent) }
    }

    private fun closeScreen(intent: PanarooUiIntent.CloseScreen) {
        viewModelScope.launch {
            global.job?.cancel()
            global.job = null
        }
    }

    private fun updateOutput(intent: PanarooUiIntent.UpdateOutput) {
        _uiState.update { it.reduce(intent) }
    }

    private fun runPanaroo(intent: PanarooUiIntent.RunPanaroo) {
        viewModelScope.launch {
            service.start(
                input = _uiState.value.inputFolder,
                output = _uiState.value.outputFolder,
                memory = _uiStateConfig.value.memory,
                parameters = createParams(_uiStateConfig.value)
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
        val params = mutableListOf<String>()
        val original = PanarooParams()
        if (config.threads > 0) {
            params.add("--threads")
            params.add(config.threads.toString())
        }
        if (config.cleanMode != CleanMode.NONE) {
            params.add("--clean-mode")
            params.add(config.cleanMode.toString())
        }
        if (config.removeInvalidGenes) {
            params.add("--remove-invalid-genes")
        }
        if (config.threshold.isNotBlank() && config.threshold != original.threshold) {
            params.add("--threshold")
            params.add(config.threshold)
        }
        if (config.familyThreshold.isNotBlank() && config.familyThreshold != original.familyThreshold) {
            params.add("--family_threshold")
            params.add(config.familyThreshold)
        }

        return params
    }

}