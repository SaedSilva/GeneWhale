package br.ufpa.genewhale.utils

import java.awt.Desktop
import java.net.URI

/**
 * Utility object for desktop-specific operations.
 */
object Desktop {
    /**
     * Opens a web browser with the specified URL.
     * @param url The URL to open in the browser.
     */
    fun openBrowser(url: String) {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                val uri = URI(url)
                desktop.browse(uri)
            }
        }
    }
}