package br.ufpa.pangenome.ui.states.tools

data class PanarooUiState(
    val inputFolder: String = "",
    val outputFolder: String = "",
    val output: List<String> = emptyList(),
)

sealed class PanarooUiIntent {
    data class ChangeInputFolder(val folder: String) : PanarooUiIntent()
    data object ClearInputFolder : PanarooUiIntent()
    data class ChangeOutputFolder(val folder: String) : PanarooUiIntent()
    data object ClearOutputFolder : PanarooUiIntent()
    data object RunPanaroo : PanarooUiIntent()
    data object ClearOutput : PanarooUiIntent()
    data class UpdateOutput(val output: String) : PanarooUiIntent()
}

fun PanarooUiState.reduce(intent: PanarooUiIntent): PanarooUiState {
    return when (intent) {
        is PanarooUiIntent.ChangeInputFolder -> this.copy(inputFolder = intent.folder)
        is PanarooUiIntent.ClearInputFolder -> this.copy(inputFolder = "")
        is PanarooUiIntent.ChangeOutputFolder -> this.copy(outputFolder = intent.folder)
        is PanarooUiIntent.ClearOutputFolder -> this.copy(outputFolder = "")
        is PanarooUiIntent.RunPanaroo -> this
        is PanarooUiIntent.ClearOutput -> this.copy(output = emptyList())
        is PanarooUiIntent.UpdateOutput -> this.copy(output = output + intent.output)
    }
}
