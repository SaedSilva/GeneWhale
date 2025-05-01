package br.ufpa.genewhale.ui.windows

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogWindow

@Composable
fun InvisibleDialog(visible: Boolean) {
    DialogWindow(
        visible = visible,
        onCloseRequest = {},
        undecorated = true,
        transparent = true
    ) {
        Surface(
            color = Color.Transparent,
            contentColor = Color.Transparent
        ) {

        }
    }
}