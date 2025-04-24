package br.ufpa.genewhale.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.ufpa.genewhale.ui.navigation.Route
import br.ufpa.genewhale.ui.screens.HomeScreen
import br.ufpa.genewhale.ui.screens.ProjectScreen
import br.ufpa.genewhale.ui.screens.tools.Panaroo
import br.ufpa.genewhale.ui.states.GlobalEffect
import br.ufpa.genewhale.ui.theme.GenomeTheme
import br.ufpa.genewhale.ui.viewmodels.Global
import br.ufpa.genewhale.ui.viewmodels.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import br.ufpa.genewhale.ui.viewmodels.tools.PanarooViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


private const val TIME_TRANSITION = 500

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val global: Global = koinInject()

    LaunchedEffect(Unit) {
        scope.launch {
            global.uiEffect.collect {
                when (it) {
                    is GlobalEffect.ShowSnackBar -> {
                        snackBarState.showSnackbar(
                            message = it.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    GenomeTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarState, modifier = Modifier.width(200.dp).height(100.dp))
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

private fun exitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(animationSpec = tween(TIME_TRANSITION)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }

private fun popExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(animationSpec = tween(TIME_TRANSITION)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }

private fun enterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(animationSpec = tween(TIME_TRANSITION)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }

private fun popEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(animationSpec = tween(TIME_TRANSITION)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }
