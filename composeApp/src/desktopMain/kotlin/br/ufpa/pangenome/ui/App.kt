package br.ufpa.pangenome.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ui.navigation.Route
import br.ufpa.pangenome.ui.screens.HomeScreen
import br.ufpa.pangenome.ui.screens.ProjectScreen
import br.ufpa.pangenome.ui.screens.tools.Panaroo
import br.ufpa.pangenome.ui.viewmodels.HomeViewModel
import br.ufpa.pangenome.ui.viewmodels.ProjectViewModel
import br.ufpa.pangenome.ui.viewmodels.tools.PanarooViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    GenomeTheme {
        Scaffold { padding ->
            NavHost(
                navController = navController,
                startDestination = Route.Home,
                modifier = Modifier.padding(padding).padding(16.dp)
            ) {
                composable<Route.Project> {
                    val viewModel: ProjectViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    ProjectScreen(
                        modifier = Modifier.fillMaxSize(),
                        state = state
                    ) {
                        viewModel.handleIntent(it)
                    }
                }
                composable<Route.Home> {
                    val viewModel: HomeViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    HomeScreen(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        onNavigateToPanarooScreen = {
                            navController.navigate(Route.Tools.Panaroo)
                        },
                    ) {
                        viewModel.handleIntent(it)
                    }
                }
                composable<Route.Tools.Panaroo> {
                    val viewModel: PanarooViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    Panaroo(modifier = Modifier.fillMaxSize(), state = state) {
                        viewModel.handleIntent(it)
                    }
                }
            }
        }
    }
}