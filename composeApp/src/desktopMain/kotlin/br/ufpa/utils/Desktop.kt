package br.ufpa.utils

import java.awt.Desktop
import java.net.URI

object Desktop {
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