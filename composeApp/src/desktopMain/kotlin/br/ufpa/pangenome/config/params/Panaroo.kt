package br.ufpa.pangenome.config.params

import kotlinx.serialization.Serializable

@Serializable
data class PanarooParams(
    val cleanMode: String = "strict",
    val removeInvalidGenes: Boolean = false,
)
