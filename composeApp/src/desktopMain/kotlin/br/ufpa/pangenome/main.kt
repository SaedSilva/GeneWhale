package br.ufpa.pangenome

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import br.ufpa.pangenome.docker.PanarooService
import br.ufpa.pangenome.ui.App
import br.ufpa.pangenome.ui.viewmodels.Global
import br.ufpa.pangenome.ui.viewmodels.HomeViewModel
import br.ufpa.pangenome.ui.viewmodels.ProjectViewModel
import br.ufpa.pangenome.ui.viewmodels.tools.PanarooViewModel
import br.ufpa.utils.Desktop
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.awt.Dimension
import java.util.*

fun main() = application {
    Locale.setDefault(Locale.ENGLISH)
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
            title = "pangenome",
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