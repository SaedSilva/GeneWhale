package br.ufpa.genewhale.ui

import br.ufpa.genewhale.params.PanarooParamsConfig
import br.ufpa.genewhale.utils.isValidFloat
import br.ufpa.genewhale.utils.isValidInt
import br.ufpa.genewhale.utils.toMB
import br.ufpa.genewhale.utils.toThreads

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
    val familyThreshold: String = "0.7", //Float
    val lenDifPercent: String = "0.98", //Float
    val familyLenDifPercent: String = "0.0", //Float
    val mergeParalogs: Boolean = false,

    val searchRadius: String = "0", //Int
    val refindPropMatch: String = "0.0", //Float
    val showRefindModeDropdown: Boolean = false,
    val refindMode: RefindMode = RefindMode.NONE,
) {
    companion object {
        fun fromEntity(entity: PanarooParamsConfig, state: PanarooParams): PanarooParams {
            return state.copy(
                memory = entity.memory,
                threads = entity.threads,
                cleanMode = CleanMode.valueOf(entity.cleanMode?.uppercase() ?: "NONE"),
                removeInvalidGenes = entity.removeInvalidGenes,
                threshold = entity.threshold.toString(),
                familyThreshold = entity.familyThreshold.toString(),
                lenDifPercent = entity.lenDifPercent.toString(),
                familyLenDifPercent = entity.familyLenDifPercent.toString(),
                mergeParalogs = entity.mergeParalogs,
                searchRadius = entity.searchRadius?.toString() ?: "",
                refindPropMatch = entity.refindPropMatch?.toString() ?: "",
                refindMode = RefindMode.valueOf(entity.refindMode?.uppercase() ?: "NONE"),
            )
        }
    }
}

sealed class PanarooParamsIntent {
    data object Save : PanarooParamsIntent()
    data object Reset : PanarooParamsIntent()

    data class ChangeMemorySlider(val memory: Float) : PanarooParamsIntent()
    data class ChangeThreadsSlider(val threads: Float) : PanarooParamsIntent()

    data class ChangeCleanMode(val cleanMode: CleanMode) : PanarooParamsIntent()
    data object ShowCleanModeDropdown : PanarooParamsIntent()
    data object HideCleanModeDropdown : PanarooParamsIntent()

    data class SetRemoveInvalidGenes(val remove: Boolean) : PanarooParamsIntent()

    data class ChangeThreshold(val threshold: String) : PanarooParamsIntent()
    data class ChangeFamilyThreshold(val threshold: String) : PanarooParamsIntent()
    data class ChangeLenDifPercent(val threshold: String) : PanarooParamsIntent()
    data class ChangeFamilyLenDifPercent(val threshold: String) : PanarooParamsIntent()
    data class SetMergeParalogs(val merge: Boolean) : PanarooParamsIntent()

    data class ChangeSearchRadius(val searchRadius: String) : PanarooParamsIntent()
    data class ChangeRefindPropMatch(val refindPropMatch: String) : PanarooParamsIntent()
    data object ShowRefindModeDropdown : PanarooParamsIntent()
    data object HideRefindModeDropdown : PanarooParamsIntent()
    data class ChangeRefindMode(val refindMode: RefindMode) : PanarooParamsIntent()
}

fun PanarooParams.reduce(intent: PanarooParamsIntent): PanarooParams {
    return when (intent) {
        is PanarooParamsIntent.Save -> this

        is PanarooParamsIntent.Reset -> PanarooParams().copy(
            maxThreads = this.maxThreads,
            maxMemory = this.maxMemory,
            threadsSlider = 1.0f / this.maxThreads,
        )

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

        is PanarooParamsIntent.ChangeLenDifPercent -> {
            if (intent.threshold.isEmpty() || intent.threshold.isValidFloat()) {
                this.copy(lenDifPercent = intent.threshold)
            } else {
                this
            }
        }

        is PanarooParamsIntent.ChangeFamilyLenDifPercent -> {
            if (intent.threshold.isEmpty() || intent.threshold.isValidFloat()) {
                this.copy(familyLenDifPercent = intent.threshold)
            } else {
                this
            }
        }

        is PanarooParamsIntent.SetMergeParalogs -> this.copy(mergeParalogs = intent.merge)

        is PanarooParamsIntent.ChangeSearchRadius -> {
            if (intent.searchRadius.isEmpty() || intent.searchRadius.isValidInt()) {
                this.copy(searchRadius = intent.searchRadius)
            } else {
                this
            }
        }

        is PanarooParamsIntent.ChangeRefindPropMatch -> {
            if (intent.refindPropMatch.isEmpty() || intent.refindPropMatch.isValidFloat()) {
                this.copy(refindPropMatch = intent.refindPropMatch)
            } else {
                this
            }
        }

        is PanarooParamsIntent.HideRefindModeDropdown -> this.copy(showRefindModeDropdown = false)
        is PanarooParamsIntent.ShowRefindModeDropdown -> this.copy(showRefindModeDropdown = true)

        is PanarooParamsIntent.ChangeRefindMode -> this.copy(refindMode = intent.refindMode)
    }
}

enum class CleanMode(val value: String) {
    NONE("none"),
    STRICT("strict"),
    MODERATE("moderate"),
    SENSITIVE("sensitive");

    override fun toString() = value.lowercase()
}

enum class RefindMode(val value: String) {
    NONE("none"),
    DEFAULT("default"),
    STRICT("strict"),
    OFF("off");

    override fun toString() = value.lowercase()
}

