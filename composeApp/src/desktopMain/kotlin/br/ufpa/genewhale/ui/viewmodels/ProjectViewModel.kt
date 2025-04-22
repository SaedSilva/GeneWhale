package br.ufpa.genewhale.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.ufpa.genewhale.ui.states.ProjectUiIntent
import br.ufpa.genewhale.ui.states.ProjectUiState
import br.ufpa.genewhale.ui.states.reduce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProjectViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ProjectUiState())
    val uiState = _uiState.asStateFlow()

    fun handleIntent(intent: ProjectUiIntent) {
        when (intent) {
            is ProjectUiIntent.ChangeSearchText -> changeSearchText(intent)
            is ProjectUiIntent.ClearSearchText -> clearSearchText(intent)
        }
    }

    private fun clearSearchText(intent: ProjectUiIntent.ClearSearchText) {
        _uiState.update { it.reduce(intent) }
    }

    private fun changeSearchText(intent: ProjectUiIntent.ChangeSearchText) {
        _uiState.update { it.reduce(intent) }
    }


}