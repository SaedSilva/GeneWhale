package br.ufpa.genewhale.ui.navigation

import kotlinx.serialization.Serializable


/**
 * Represents the different routes in the application.
 */
@Serializable
sealed class Route {
    @Serializable
    data object Home: Route()
    @Serializable
    data object Project: Route()

    sealed class Tools: Route() {
        @Serializable
        data object Panaroo: Tools()
    }
}