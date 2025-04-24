package br.ufpa.genewhale

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.rememberNavController
import br.ufpa.genewhale.docker.PanarooService
import br.ufpa.genewhale.ui.App
import br.ufpa.genewhale.ui.viewmodels.Global
import br.ufpa.genewhale.ui.viewmodels.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import br.ufpa.genewhale.ui.viewmodels.tools.PanarooViewModel
import br.ufpa.genewhale.web.WebService
import br.ufpa.genewhale.web.WebServiceJavaImpl
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pangenome.composeapp.generated.resources.Res
import pangenome.composeapp.generated.resources.genewhalegenewhaleicon
import java.awt.Dimension
import java.util.*

fun main() = application {
    Locale.setDefault(Locale.US)
    KoinApplication(
        application = { modules(appModule, viewModelsModule, webModules) }
    ) {
        val global: Global = koinInject()
        val navController = rememberNavController()
        Window(
            onCloseRequest = {
                global.stopAll()
                exitApplication()
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
            App(navController)
        }
    }
}

private val appModule = module {
    single { Global(get(), get()) }
    single { PanarooService() }
}

private val viewModelsModule = module {
    viewModelOf(::ProjectViewModel)
    viewModelOf(::PanarooViewModel)
    viewModelOf(::HomeViewModel)
}

private val webModules = module {
    single <WebService>{ WebServiceJavaImpl() }
}