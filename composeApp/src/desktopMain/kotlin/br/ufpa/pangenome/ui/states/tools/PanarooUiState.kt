package br.ufpa.pangenome.ui.states.tools

import br.ufpa.utils.toMB
import br.ufpa.utils.toThreads

data class PanarooUiState(
    val inputFolder: String = "",
    val outputFolder: String = "",
    val output: List<String> = emptyList(),
    val isRunning: Boolean = false,
    val config: PanarooConfig = PanarooConfig(),
)

sealed class PanarooUiIntent {
    data class ChangeInputFolder(val folder: String) : PanarooUiIntent()
    data object ClearInputFolder : PanarooUiIntent()
    data class ChangeOutputFolder(val folder: String) : PanarooUiIntent()
    data object ClearOutputFolder : PanarooUiIntent()
    data object RunPanaroo : PanarooUiIntent()
    data object ClearOutput : PanarooUiIntent()
    data class UpdateOutput(val output: String) : PanarooUiIntent()
    data class ConfigIntent(val intent: PanarooConfigIntent) : PanarooUiIntent()
    data object CloseScreen : PanarooUiIntent()
}

fun PanarooUiState.reduce(intent: PanarooUiIntent): PanarooUiState {
    return when (intent) {
        is PanarooUiIntent.ChangeInputFolder -> this.copy(inputFolder = intent.folder)
        is PanarooUiIntent.ClearInputFolder -> this.copy(inputFolder = "")
        is PanarooUiIntent.ChangeOutputFolder -> this.copy(outputFolder = intent.folder)
        is PanarooUiIntent.ClearOutputFolder -> this.copy(outputFolder = "")
        is PanarooUiIntent.RunPanaroo -> this
        is PanarooUiIntent.ClearOutput -> this.copy(output = emptyList())
        is PanarooUiIntent.UpdateOutput -> this.copy(output = this.output + intent.output)
        is PanarooUiIntent.CloseScreen -> this
        is PanarooUiIntent.ConfigIntent -> this.copy(config = this.config.reduce(intent.intent))
    }
}

data class PanarooConfig(
    val memorySlider: Float = 0.0f,
    val memory: Long = 0L,
    val maxMemory: Long = 0L,

    val threadsSlider: Float = 0.0f,
    val threads: Int = 1,
    val maxThreads: Int = 0,

    val cleanMode: CleanMode = CleanMode.NONE,
    val removeInvalidGenes: Boolean = false,
)

sealed class PanarooConfigIntent {
    data class ChangeMemorySlider(val memory: Float) : PanarooConfigIntent()
    data class ChangeThreadsSlider(val threads: Float) : PanarooConfigIntent()
}

fun PanarooConfig.reduce(intent: PanarooConfigIntent): PanarooConfig {
    return when (intent) {
        is PanarooConfigIntent.ChangeMemorySlider -> this.copy(
            memorySlider = intent.memory,
            memory = intent.memory.toMB(this.maxMemory)
        )

        is PanarooConfigIntent.ChangeThreadsSlider -> {
            val threadsF = if (intent.threads == 0.0f) {
                1.0f / this.maxThreads
            } else {
                intent.threads
            }
            this.copy(
                threadsSlider = threadsF,
                threads = threadsF.toThreads(this.maxThreads)
            )
        }
    }
}

enum class CleanMode {
    NONE,
    STRICT,
    MODERATE,
    SENSITIVE;

    override fun toString(): String {
        return when (this) {
            NONE -> "none"
            STRICT -> "strict"
            MODERATE -> "moderate"
            SENSITIVE -> "sensitive"
        }
    }
}

