package br.ufpa.genewhale.global

data class GlobalState(
    val actionInProgress: Boolean = false,
    val textAction: String? = null,
    val memoryBytes: Long = 0,
    val threads: Int = 0,
)

sealed class GlobalIntent {
    data object CloseApplication : GlobalIntent()
    data class ShowActionInProgress(val text: String) : GlobalIntent()
    data object HideActionInProgress : GlobalIntent()
}

fun GlobalState.reduce(
    intent: GlobalIntent,
): GlobalState {
    return when (intent) {
        is GlobalIntent.CloseApplication -> copy(actionInProgress = true, textAction = "Stopping Containers")
        is GlobalIntent.ShowActionInProgress -> copy(actionInProgress = true, textAction = intent.text)
        is GlobalIntent.HideActionInProgress -> copy(actionInProgress = false, textAction = null)
    }
}

sealed class GlobalEffect {
    data class ShowSnackBar(val message: String) : GlobalEffect()
    data class ShowSnackBarWithAction(
        val message: String,
        val actionLabel: String,
        val action: () -> Unit
    ) : GlobalEffect()

    data object CloseApplication : GlobalEffect()
}
