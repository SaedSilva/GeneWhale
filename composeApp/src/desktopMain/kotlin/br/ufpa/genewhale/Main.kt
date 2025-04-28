package br.ufpa.genewhale

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import br.ufpa.genewhale.di.appModule
import br.ufpa.genewhale.di.panarooModule
import br.ufpa.genewhale.di.servicesModule
import br.ufpa.genewhale.di.viewModelsModule
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
        application = {
            modules(
                appModule,
                servicesModule,
                viewModelsModule,
                panarooModule
            )
        }
    ) {
        val global: Global = koinInject()
        val state by global.uiState.collectAsState()
        val navController = rememberNavController()

        LaunchedEffect(Unit) {
            global.uiEffect.collect {
                if (it is GlobalEffect.CloseApplication) {
                    exitApplication()
                }
            }
        }

        if (state.isClosing) {
            StoppingContainersWindow()
        } else {
            MainWindow(global, navController)
        }
    }
}