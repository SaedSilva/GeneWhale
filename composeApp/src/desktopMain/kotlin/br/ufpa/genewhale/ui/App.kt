package br.ufpa.genewhale.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.ufpa.genewhale.global.Global
import br.ufpa.genewhale.global.GlobalEffect
import br.ufpa.genewhale.theme.GenomeTheme
import br.ufpa.genewhale.ui.navigation.*
import br.ufpa.genewhale.ui.screens.HomeScreen
import br.ufpa.genewhale.ui.screens.ProjectScreen
import br.ufpa.genewhale.ui.viewmodels.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.awt.Window

@Composable
@Preview
fun App(
    navController: NavHostController,
    global: Global,
    window: Window? = null
) {
    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        handleGlobalEffects(global, snackBarState)
        global.testVersion()
    }

    GenomeTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarState,
                    modifier = Modifier
                        .widthIn(min = 200.dp, max = 350.dp)
                        .height(100.dp)
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = Route.Home,
                modifier = Modifier.padding(padding).padding(16.dp),
                enterTransition = enterTransition(),
                popEnterTransition = popEnterTransition(),
                exitTransition = exitTransition(),
                popExitTransition = popExitTransition()
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
                    val configState by viewModel.uiStateConfig.collectAsStateWithLifecycle()
                    Panaroo(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        window = window,
                        configState = configState,
                        onNavigateBack = {
                            navController.popBackStack()
                        },
                        onConfigIntent = {
                            viewModel.handleConfigIntent(it)
                        }
                    ) {
                        viewModel.handleIntent(it)
                    }
                }
            }
        }
    }
}

private fun CoroutineScope.handleGlobalEffects(
    global: Global,
    snackBarState: SnackbarHostState
) {
    launch {
        global.uiEffect.collect {
            when (it) {
                is GlobalEffect.ShowSnackBar -> {
                    snackBarState.showSnackbar(
                        message = it.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is GlobalEffect.ShowSnackBarWithAction -> {
                    when (
                        snackBarState.showSnackbar(
                            message = it.message,
                            actionLabel = it.actionLabel,
                            duration = SnackbarDuration.Long
                        )
                    ) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> it.action()
                    }
                }

                is GlobalEffect.CloseApplication -> {}
            }
        }
    }
}