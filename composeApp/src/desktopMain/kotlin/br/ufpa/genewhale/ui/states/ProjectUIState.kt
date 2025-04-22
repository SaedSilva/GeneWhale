package br.ufpa.genewhale.ui.states

data class ProjectUiState(
    val searchText: String = ""
)

sealed class ProjectUiIntent {
    data class ChangeSearchText(val text: String) : ProjectUiIntent()
    data object ClearSearchText : ProjectUiIntent()
}

fun ProjectUiState.reduce(
    intent: ProjectUiIntent,
): ProjectUiState {
    return when (intent) {
        is ProjectUiIntent.ChangeSearchText -> this.copy(searchText = intent.text)
        is ProjectUiIntent.ClearSearchText -> this.copy(searchText = "")
    }
}