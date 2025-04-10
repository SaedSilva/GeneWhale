package br.ufpa.pangenome.ui.viewmodels.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufpa.pangenome.docker.PanarooService
import br.ufpa.pangenome.ui.states.tools.PanarooUiIntent
import br.ufpa.pangenome.ui.states.tools.PanarooUiState
import br.ufpa.pangenome.ui.states.tools.reduce
import br.ufpa.pangenome.ui.viewmodels.Global
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PanarooViewModel(
    private val service: PanarooService,
    private val global: Global,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PanarooUiState())
    val uiState = _uiState.asStateFlow()

    fun handleIntent(intent: PanarooUiIntent) {
        when (intent) {
            is PanarooUiIntent.ChangeInputFolder -> changeInputFolder(intent)
            is PanarooUiIntent.ClearInputFolder -> clearInputFolder(intent)
            is PanarooUiIntent.ChangeOutputFolder -> changeOutputFolder(intent)
            is PanarooUiIntent.ClearOutputFolder -> clearOutputFolder(intent)
            is PanarooUiIntent.ClearOutput -> clearOutput(intent)
            is PanarooUiIntent.RunPanaroo -> runPanaroo(intent)
            is PanarooUiIntent.UpdateOutput -> updateOutput(intent)
        }
    }

    private fun updateOutput(intent: PanarooUiIntent.UpdateOutput) {
        _uiState.update { it.reduce(intent) }
    }

    private fun runPanaroo(intent: PanarooUiIntent.RunPanaroo) {
        viewModelScope.launch {
            global.job = service.start(_uiState.value.inputFolder, _uiState.value.outputFolder)
                .onEach {
                    handleIntent(PanarooUiIntent.UpdateOutput(it))
                }
                .onCompletion {
                    handleIntent(PanarooUiIntent.UpdateOutput("Process completed successfully."))
                }
                .launchIn(viewModelScope)
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


}