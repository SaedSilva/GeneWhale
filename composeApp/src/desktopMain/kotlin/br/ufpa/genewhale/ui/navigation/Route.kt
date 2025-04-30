package br.ufpa.genewhale.ui.navigation

import kotlinx.serialization.Serializable


/**
 * Represents the different routes in the application.
 */

sealed class Route {

    @Serializable
    data object Home: Route()

    @Serializable
    data object Project: Route()

    sealed class Tools: Route() {

        @Serializable
        sealed class Panaroo: Tools() {

            @Serializable
            data object Graph: Panaroo()

            @Serializable
            data object BasicUsage: Panaroo()

            @Serializable
            data object Config: Panaroo()
        }
    }
}

/**
 * Represents the panaroo scope.
 */
interface PanarooScope {
    companion object {
        /**
         * The ID of the panaroo scope.
         */
        const val ID = "PanarooScopeId"
    }
}