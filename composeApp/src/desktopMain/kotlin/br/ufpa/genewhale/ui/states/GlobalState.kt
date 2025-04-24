package br.ufpa.genewhale.ui.states

data class GlobalState(
    val isLoading: Boolean = false,
    val memoryBytes: Long = 0,
    val threads: Int = 0,
)

sealed class GlobalIntent {

}

sealed class GlobalEffect {
    data class ShowSnackBar(val message: String) : GlobalEffect()
    data class ShowSnackBarWithAction(
        val message: String,
        val actionLabel: String,
        val action: () -> Unit
    ) : GlobalEffect()
}
