package br.ufpa.genewhale.ui.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.NavHostController
import br.ufpa.genewhale.ui.App
import br.ufpa.genewhale.ui.states.GlobalIntent
import br.ufpa.genewhale.ui.viewmodels.Global
import org.jetbrains.compose.resources.painterResource
import pangenome.composeapp.generated.resources.Res
import pangenome.composeapp.generated.resources.genewhalegenewhaleicon
import java.awt.Dimension

@Composable
fun MainWindow(
    global: Global,
    navController: NavHostController
) {
    Window(
        onCloseRequest = {
            global.handleIntent(GlobalIntent.CloseApplication)
        },
        title = "GeneWhale",
        state = rememberWindowState(placement = WindowPlacement.Maximized),
        icon = painterResource(Res.drawable.genewhalegenewhaleicon),
        onKeyEvent = {
            if (it.key == Key.Escape) {
                navController.navigateUp()
                true
            } else {
                false
            }
        }
    ) {
        window.minimumSize = Dimension(1280, 720)
        App(navController, global)
    }
}