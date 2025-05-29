package br.ufpa.genewhale.home

data class HomeUIState(
    val isLoading: Boolean = false,
    val dockerRunning: Boolean = false,
)

sealed class HomeUiIntent {
    data object TestDocker : HomeUiIntent()
    data object StartLoading : HomeUiIntent()
    data object StopLoading : HomeUiIntent()
    data object DockerRunning : HomeUiIntent()
    data object DockerNotRunning : HomeUiIntent()
}

fun HomeUIState.reduce(
    intent: HomeUiIntent,
): HomeUIState {
    return when (intent) {
        is HomeUiIntent.TestDocker -> this
        is HomeUiIntent.StartLoading -> this.copy(isLoading = true)
        is HomeUiIntent.StopLoading -> this.copy(isLoading = false)
        is HomeUiIntent.DockerRunning -> this.copy(dockerRunning = true)
        is HomeUiIntent.DockerNotRunning -> this.copy(dockerRunning = false)
    }
}