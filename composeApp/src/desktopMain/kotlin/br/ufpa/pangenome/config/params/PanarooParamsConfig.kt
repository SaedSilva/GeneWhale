package br.ufpa.pangenome.config.params

import br.ufpa.pangenome.ui.states.tools.CleanMode
import br.ufpa.pangenome.ui.states.tools.PanarooParams
import kotlinx.serialization.Serializable

@Serializable
data class PanarooParamsConfig(
    // General
    val threads: Int = 1,
    val codonTable: Int = 11,

    // Mode
    val cleanMode: String? = null,
    val removeInvalidGenes: Boolean = false,

    // Matching
    val threshold: Float = 0.98f,
    val familyThreshold: Float = 0.7f,
    val lenDifPercent: Float = 0.98f,
    val familyLenDifPercent: Float = 0.0f,
    val mergeParalogs: Boolean = false,

    // Refind
    val searchRadius: Int? = null,
    val refindPropMatch: Float? = null,
    val refindMode: String = "default",

    // Graph correction
    val minTrailingSupport: Int? = null,
    val trailingRecursive: Int? = null,
    val edgeSupportThreshold: Float? = null,
    val lengthOutlierSupportProportion: Float? = null,
    val removeByConsensus: Boolean = false,
    val highVarFlag: Int? = null,
    val minEdgeSupportSV: Int? = null,
    val allSeqInGraph: Boolean = false,
    val noCleanEdges: Boolean = false,

    // Gene alignment
    val alignment: String? = null,
    val aligner: String = "mafft",
    val codons: Boolean = false,
    val coreThreshold: Float = 0.95f,
    val coreSubset: Int? = null,
    val coreEntropyFilter: Float? = null,
) {
    companion object {
        fun fromState(state: PanarooParams): PanarooParamsConfig {
            val config = PanarooParamsConfig()
            return config.copy(
                threads = if (state.threads > 0) state.threads else config.threads,
                cleanMode = if (state.cleanMode != CleanMode.NONE) state.cleanMode.toString() else null,
                removeInvalidGenes = state.removeInvalidGenes,
                threshold = if (state.threshold != config.threshold.toString()) state.threshold.toFloat() else config.threshold,
                familyThreshold = if (state.familyThreshold != config.familyThreshold.toString()) state.familyThreshold.toFloat() else config.familyThreshold,
            )
        }
    }
}
