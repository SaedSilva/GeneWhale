package br.ufpa.genewhale.ui

enum class CleanMode(val value: String) {
    NONE("none"),
    STRICT("strict"),
    MODERATE("moderate"),
    SENSITIVE("sensitive");

    override fun toString() = value.lowercase()
}