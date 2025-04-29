package br.ufpa.genewhale.ui

enum class RefindMode(val value: String) {
    NONE("none"),
    DEFAULT("default"),
    STRICT("strict"),
    OFF("off");

    override fun toString() = value.lowercase()
}