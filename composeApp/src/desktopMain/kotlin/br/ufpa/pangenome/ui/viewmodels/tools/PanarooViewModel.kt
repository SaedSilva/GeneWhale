package br.ufpa.pangenome.ui.viewmodels.tools

import androidx.lifecycle.ViewModel
import br.ufpa.pangenome.ui.states.tools.PanarooUiIntent
import br.ufpa.pangenome.ui.states.tools.PanarooUiState
import br.ufpa.pangenome.ui.states.tools.reduce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PanarooViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PanarooUiState())
    val uiState = _uiState.asStateFlow()

    fun handleIntent(intent: PanarooUiIntent) {
        when (intent) {
            is PanarooUiIntent.ChangeInputFolder -> changeInputFolder(intent)
            is PanarooUiIntent.ClearInputFolder -> clearInputFolder(intent)
            is PanarooUiIntent.ChangeOutputFolder -> changeOutputFolder(intent)
            is PanarooUiIntent.ClearOutputFolder -> clearOutputFolder(intent)
        }
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