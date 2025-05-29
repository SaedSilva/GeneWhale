package br.ufpa.genewhale.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(

) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        handleIntent(HomeUiIntent.TestDocker)
    }

    fun handleIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.TestDocker -> testDocker(intent)
            is HomeUiIntent.StartLoading -> startLoading(intent)
            is HomeUiIntent.StopLoading -> stopLoading(intent)
            is HomeUiIntent.DockerNotRunning -> dockerNotRunning(intent)
            is HomeUiIntent.DockerRunning -> dockerRunning(intent)
        }
    }

    private fun dockerRunning(intent: HomeUiIntent) {
        _uiState.update { it.reduce(intent) }
    }

    private fun dockerNotRunning(intent: HomeUiIntent) {
        _uiState.update { it.reduce(intent) }
    }

    private fun testDocker(intent: HomeUiIntent.TestDocker) {
        _uiState.update { it.reduce(intent) }
        viewModelScope.launch {

        }
    }

    private fun startLoading(intent: HomeUiIntent.StartLoading) {
        _uiState.update { it.reduce(intent) }
    }

    private fun stopLoading(intent: HomeUiIntent.StopLoading) {
        _uiState.update { it.reduce(intent) }
    }

}