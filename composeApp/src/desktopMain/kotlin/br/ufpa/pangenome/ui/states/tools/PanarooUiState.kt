package br.ufpa.pangenome.ui.states.tools

import br.ufpa.pangenome.utils.isValidFloat
import br.ufpa.pangenome.utils.toMB
import br.ufpa.pangenome.utils.toThreads

data class PanarooUiState(
    val inputFolder: String = "",
    val outputFolder: String = "",
    val output: List<String> = emptyList(),
    val isRunning: Boolean = false,
)

sealed class PanarooUiIntent {
    data class ChangeInputFolder(val folder: String) : PanarooUiIntent()
    data object ClearInputFolder : PanarooUiIntent()
    data class ChangeOutputFolder(val folder: String) : PanarooUiIntent()
    data object ClearOutputFolder : PanarooUiIntent()
    data object RunPanaroo : PanarooUiIntent()
    data object ClearOutput : PanarooUiIntent()
    data class UpdateOutput(val output: String) : PanarooUiIntent()
    data object CloseScreen : PanarooUiIntent()
    data object OpenDocs : PanarooUiIntent()
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
        is PanarooUiIntent.OpenDocs -> this
    }
}

data class PanarooParams(
    val memorySlider: Float = 0.0f,
    val memory: Long = 0L,
    val maxMemory: Long = 0L,

    val threadsSlider: Float = 0.0f,
    val threads: Int = 1,
    val maxThreads: Int = 0,

    val cleanMode: CleanMode = CleanMode.NONE,
    val showCleaModeDropdown: Boolean = false,

    val removeInvalidGenes: Boolean = false,

    val threshold: String = "0.98", //Float
    val familyThreshold: String = "0.7" //Float

)

sealed class PanarooParamsIntent {
    data class ChangeMemorySlider(val memory: Float) : PanarooParamsIntent()
    data class ChangeThreadsSlider(val threads: Float) : PanarooParamsIntent()

    data class ChangeCleanMode(val cleanMode: CleanMode) : PanarooParamsIntent()
    data object ShowCleanModeDropdown : PanarooParamsIntent()
    data object HideCleanModeDropdown : PanarooParamsIntent()

    data class SetRemoveInvalidGenes(val remove: Boolean) : PanarooParamsIntent()

    data class ChangeThreshold(val threshold: String) : PanarooParamsIntent()
    data class ChangeFamilyThreshold(val threshold: String) : PanarooParamsIntent()
}

fun PanarooParams.reduce(intent: PanarooParamsIntent): PanarooParams {
    return when (intent) {
        is PanarooParamsIntent.ChangeMemorySlider -> this.copy(
            memorySlider = intent.memory,
            memory = intent.memory.toMB(this.maxMemory)
        )

        is PanarooParamsIntent.ChangeThreadsSlider -> {
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

        is PanarooParamsIntent.ChangeCleanMode -> this.copy(cleanMode = intent.cleanMode)
        is PanarooParamsIntent.HideCleanModeDropdown -> this.copy(showCleaModeDropdown = false)
        is PanarooParamsIntent.ShowCleanModeDropdown -> this.copy(showCleaModeDropdown = true)

        is PanarooParamsIntent.SetRemoveInvalidGenes -> this.copy(removeInvalidGenes = intent.remove)

        is PanarooParamsIntent.ChangeThreshold -> {
            if (intent.threshold.isEmpty() || intent.threshold.isValidFloat()) {
                this.copy(threshold = intent.threshold)
            } else {
                this
            }
        }

        is PanarooParamsIntent.ChangeFamilyThreshold -> {
            if (intent.threshold.isEmpty() || intent.threshold.isValidFloat()) {
                this.copy(familyThreshold = intent.threshold)
            } else {
                this
            }
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

    fun fromString(value: String): CleanMode {
        return when (value) {
            "none" -> NONE
            "strict" -> STRICT
            "moderate" -> MODERATE
            "sensitive" -> SENSITIVE
            else -> NONE
        }
    }
}

