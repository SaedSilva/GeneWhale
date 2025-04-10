package br.ufpa.pangenome.ui.states.tools

data class PanarooUiState(
    val inputFolder: String = "",
    val outputFolder: String = "",
)

sealed class PanarooUiIntent {
    data class ChangeInputFolder(val folder: String) : PanarooUiIntent()
    data object ClearInputFolder : PanarooUiIntent()
    data class ChangeOutputFolder(val folder: String) : PanarooUiIntent()
    data object ClearOutputFolder : PanarooUiIntent()
}

fun PanarooUiState.reduce(intent: PanarooUiIntent): PanarooUiState {
    return when (intent) {
        is PanarooUiIntent.ChangeInputFolder -> this.copy(inputFolder = intent.folder)
        is PanarooUiIntent.ClearInputFolder -> this.copy(inputFolder = "")
        is PanarooUiIntent.ChangeOutputFolder -> this.copy(outputFolder = intent.folder)
        is PanarooUiIntent.ClearOutputFolder -> this.copy(outputFolder = "")
    }
}
