package br.ufpa.pangenome.ui.viewmodels

import br.ufpa.pangenome.docker.PanarooService
import br.ufpa.pangenome.ui.states.GlobalState
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class Global(
    private val panarooService: PanarooService
) {
    private val _uiState = MutableStateFlow(GlobalState())
    val uiState = _uiState.asStateFlow()

    var job: Job? = null

    fun stopAll() {
        runBlocking {
            val panaroo = async { panarooService.stop() }
            panaroo.await()
        }
    }
}