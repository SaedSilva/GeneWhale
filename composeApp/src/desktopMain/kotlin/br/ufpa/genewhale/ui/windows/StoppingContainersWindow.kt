package br.ufpa.genewhale.ui.windows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import br.ufpa.genewhale.theme.LightColors
import br.ufpa.genewhale.theme.GenomeTheme

@Composable
@Deprecated(level = DeprecationLevel.HIDDEN, message = "Blocking UI")
fun StoppingContainersWindow() {
    Window(
        onCloseRequest = {},
        transparent = true,
        undecorated = true,
        resizable = false,
        state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(width = 300.dp, height = 100.dp)
        )
    ) {
        GenomeTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = LightColors.background.copy(0.9f)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Stopping containers...")
                    LinearProgressIndicator()
                }
            }
        }
    }
}