package br.ufpa.genewhale

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.ufpa.genewhale.docker.PanarooService
import br.ufpa.genewhale.ui.App
import br.ufpa.genewhale.ui.viewmodels.Global
import br.ufpa.genewhale.ui.viewmodels.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import br.ufpa.genewhale.ui.viewmodels.tools.PanarooViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.awt.Dimension
import java.util.*

fun main() = application {
    Locale.setDefault(Locale.US)
    KoinApplication(
        application = {
            modules(appModule, viewModelsModule)
        }
    ) {
        val global: Global = koinInject()
        Window(
            onCloseRequest = {
                global.stopAll()
                exitApplication()
            },
            title = "GeneWhale",
            state = rememberWindowState(
                placement = WindowPlacement.Maximized
            )
        ) {
            window.minimumSize = Dimension(1280, 720)
            App()
        }
    }
}

private val appModule = module {
    single { Global(get()) }
    single { PanarooService() }
}

val viewModelsModule = module {
    viewModelOf(::ProjectViewModel)
    viewModelOf(::PanarooViewModel)
    viewModelOf(::HomeViewModel)
}