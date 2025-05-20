package br.ufpa.genewhale.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
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
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import java.awt.Window

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
    global: Global,
    window: Window? = null
) {
    val snackBarState = remember { SnackbarHostState() }
    val state by global.uiState.collectAsState()

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

            if (state.actionInProgress) {
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false,
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    )
                ) {
                    OutlinedCard {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(state.textAction ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = Route.Home,
                modifier = Modifier.padding(padding),
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
                        onNavigateToPanarooScreen = { navController.navigate(Route.Tools.Panaroo.Graph) },
                    ) {
                        viewModel.handleIntent(it)
                    }
                }
                navigation<Route.Tools.Panaroo.Graph>(startDestination = Route.Tools.Panaroo.BasicUsage) {
                    composable<Route.Tools.Panaroo.BasicUsage> {
                        val panarooScope = getKoin().getOrCreateScope<PanarooScope>(PanarooScope.ID)
                        val viewModel: PanarooViewModel = panarooScope.get()
                        val state by viewModel.uiState.collectAsStateWithLifecycle()
                        Panaroo(
                            modifier = Modifier.fillMaxSize(),
                            state = state,
                            window = window,
                            onNavigateBack = {
                                panarooScope.close()
                                navController.navigateUp()
                            },
                            onNavigateToConfigure = { navController.navigate(Route.Tools.Panaroo.Config) },
                        ) {
                            viewModel.handleIntent(it)
                        }
                    }
                    composable<Route.Tools.Panaroo.Config> {
                        val panarooScope = getKoin().getOrCreateScope<PanarooScope>(PanarooScope.ID)
                        val viewModel: PanarooViewModel = panarooScope.get()
                        val state by viewModel.uiStateConfig.collectAsStateWithLifecycle()
                        Text(state.toString())
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
            println(it)
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
                        SnackbarResult.ActionPerformed -> it.action.invoke()
                    }
                }

                is GlobalEffect.CloseApplication -> {}
            }
        }
    }
}