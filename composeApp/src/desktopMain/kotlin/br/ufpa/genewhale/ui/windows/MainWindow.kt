package br.ufpa.genewhale.ui.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.rememberWindowState
import br.ufpa.genewhale.global.Global
import br.ufpa.genewhale.global.GlobalIntent
import br.ufpa.genewhale.ui.App
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import pangenome.composeapp.generated.resources.Res
import pangenome.composeapp.generated.resources.genewhalegenewhaleicon
import java.awt.Dimension

@Composable
fun MainWindow(
    global: Global = koinInject()
) {
    Window(
        onCloseRequest = {
            global.handleIntent(GlobalIntent.CloseApplication)
        },
        title = "GeneWhale",
        state = rememberWindowState(placement = WindowPlacement.Maximized),
        icon = painterResource(Res.drawable.genewhalegenewhaleicon),
        onKeyEvent = {
            when {
                it.key == Key.Escape -> true // Lock the escape key for prevent navigate back
                else -> false
            }
        }
    ) {
        val state by global.uiState.collectAsState()
        window.isEnabled = state.isClickable
        window.minimumSize = Dimension(1280, 720)
        App(
            global = global,
            window = window
        )
    }
}
