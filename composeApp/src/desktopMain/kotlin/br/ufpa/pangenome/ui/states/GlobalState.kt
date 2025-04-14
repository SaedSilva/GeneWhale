package br.ufpa.pangenome.ui.states

data class GlobalState(
    val isLoading: Boolean = false,
    val memoryBytes: Long = 0,
    val threads: Int = 0,
)
