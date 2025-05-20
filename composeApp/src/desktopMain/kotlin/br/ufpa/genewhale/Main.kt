package br.ufpa.genewhale

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.application
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.ufpa.genewhale.di.modules
import br.ufpa.genewhale.global.Global
import br.ufpa.genewhale.global.GlobalEffect
import br.ufpa.genewhale.ui.windows.MainWindow
import br.ufpa.genewhale.ui.windows.StoppingContainersWindow
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import java.util.*

fun main() = application {
    Locale.setDefault(Locale.US)
    KoinApplication(
        application = { modules(modules) }
    ) {
        val global: Global = koinInject()

        LaunchedEffect(Unit) {
            global.uiEffect.collect {
                if (it is GlobalEffect.CloseApplication) {
                    exitApplication()
                }
            }
        }

        MainWindow(global)
    }
}