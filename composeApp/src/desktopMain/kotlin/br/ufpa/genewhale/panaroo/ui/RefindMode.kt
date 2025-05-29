package br.ufpa.genewhale.panaroo.ui

enum class RefindMode(val value: String) {
    NONE("none"),
    DEFAULT("default"),
    STRICT("strict"),
    OFF("off");

    override fun toString() = value.lowercase()
}