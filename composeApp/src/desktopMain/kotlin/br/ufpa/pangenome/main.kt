package br.ufpa.pangenome

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.ufpa.pangenome.docker.DockerRepositoryProcess
import br.ufpa.pangenome.docker.IDockerRepository
import br.ufpa.pangenome.ui.App
import br.ufpa.pangenome.ui.viewmodels.HomeViewModel
import br.ufpa.pangenome.ui.viewmodels.ProjectViewModel
import br.ufpa.pangenome.ui.viewmodels.tools.PanarooViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "pangenome",
    ) {
        window.minimumSize = Dimension(800, 600)
        KoinApplication(
            application = {
                modules(appModule, viewModelsModule)
            }
        ) {
            App()
        }
    }
}

private val appModule = module {
    single <IDockerRepository> { DockerRepositoryProcess() }
}

/*val ktorModule = module {
    single { HttpClient(CIO) }
}*/

val viewModelsModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProjectViewModel)
    viewModelOf(::PanarooViewModel)
}