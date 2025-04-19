package br.ufpa.pangenome.ui.screens.tools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ThemeDefaults
import br.ufpa.pangenome.ui.components.*
import br.ufpa.pangenome.ui.states.tools.*
import io.github.vinceglb.filekit.path

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Panaroo(
    modifier: Modifier = Modifier,
    state: PanarooUiState,
    configState: PanarooParams,
    onNavigateBack: () -> Unit,
    onConfigIntent: (PanarooParamsIntent) -> Unit,
    onIntent: (PanarooUiIntent) -> Unit,
) {
    var index by remember { mutableStateOf(0) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.clickable {
                    onNavigateBack()
                }.align(Alignment.TopStart), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                Text("Back", fontSize = 18.sp)
            }
            Text(
                "Panaroo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            OutlinedButton(
                modifier = Modifier.align(Alignment.TopEnd).height(32.dp),
                onClick = {
                    onIntent(PanarooUiIntent.OpenDocs)
                },
                shape = ThemeDefaults.ButtonShape
            ) {
                Text("📚 Open documentation", lineHeight = 16.sp)
            }
        }

        PickFolder(
            modifier = Modifier.fillMaxWidth(),
            value = state.inputFolder,
            onChangeValue = { onIntent(PanarooUiIntent.ChangeInputFolder(it)) },
            onClickClear = { onIntent(PanarooUiIntent.ClearInputFolder) },
            tooltip = "Select gff input folder with files",
            placeHolder = "Select input...",
            onClickButton = { },
            onResult = { it?.let { onIntent(PanarooUiIntent.ChangeInputFolder(it.path)) } }
        )

        PickFolder(
            modifier = Modifier.fillMaxWidth(),
            value = state.outputFolder,
            onChangeValue = { onIntent(PanarooUiIntent.ChangeOutputFolder(it)) },
            onClickClear = { onIntent(PanarooUiIntent.ClearOutputFolder) },
            tooltip = "Select output folder",
            placeHolder = "Select output...",
            onClickButton = { },
            onResult = { it?.let { onIntent(PanarooUiIntent.ChangeOutputFolder(it.path)) } }
        )


        OutlinedButton(
            modifier = Modifier.height(32.dp),
            onClick = {
                onIntent(PanarooUiIntent.ClearOutput)
                onIntent(PanarooUiIntent.RunPanaroo)
            },
            shape = ThemeDefaults.ButtonShape
        ) {
            Text("Run Panaroo", lineHeight = 16.sp)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SecondaryTabRow(index, modifier = Modifier.width(200.dp)) {
                MyTab(
                    modifier = Modifier,
                    selected = 0 == index,
                    onClick = { index = 0 }
                ) {
                    Text("Terminal")
                }
                MyTab(
                    modifier = Modifier,
                    selected = 1 == index,
                    onClick = { index = 1 }
                ) {
                    Text("Config")
                }
            }

            when (index) {
                0 -> {
                    Terminal(modifier = Modifier.fillMaxWidth(), output = state.output)
                }

                else -> {
                    Config(
                        modifier = Modifier.fillMaxWidth(),
                        state = configState,
                        onIntent = {
                            onConfigIntent(it)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PanarooPreview() {
    GenomeTheme {
        Panaroo(
            modifier = Modifier.fillMaxSize(), state = PanarooUiState(), onNavigateBack = {},
            configState = PanarooParams(),
            onConfigIntent = {}
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Config(modifier: Modifier = Modifier, state: PanarooParams, onIntent: (PanarooParamsIntent) -> Unit) {
    val cleanModeTooltipState = rememberTooltipState(isPersistent = true)

    Column(modifier = modifier) {
        Row {
            MemorySlider(
                modifier = Modifier.weight(1f),
                value = state.memorySlider,
                onValueChange = {
                    onIntent(PanarooParamsIntent.ChangeMemorySlider(it))
                },
                maxMemory = state.maxMemory,
                selectedMemory = state.memory,
            )
            VerticalDivider(modifier = Modifier.height(72.dp).padding(end = 4.dp, start = 4.dp))
            ThreadsSlider(
                modifier = Modifier.weight(1f),
                value = state.threadsSlider,
                onValueChange = { onIntent(PanarooParamsIntent.ChangeThreadsSlider(it)) },
                maxThreads = state.maxThreads,
                threads = state.threads
            )
        }
        HorizontalDivider()
        Row {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Clean mode")
                    TooltipArea(
                        tooltip = {
                            MyTooltip(
                                tooltip = "The stringency mode at which to run panaroo. Must be one of 'strict','moderate' or 'sensitive'. Each of these modes can be fine tuned using the additional parameters in the 'Graph correction' section.\n" +
                                        "strict: " +
                                        "Requires fairly strong evidence (present in  at least 5% of genomes) to keep likely contaminant genes. Will remove genes that are refound more often than they were called originally.\n" +
                                        "moderate: " +
                                        "Requires moderate evidence (present in  at least 1% of genomes) to keep likely contaminant genes. Keeps genes that are refound more often than they were called originally.\n" +
                                        "sensitive: " +
                                        "Does not delete any genes and only performes merge and refinding operations. Useful if rare plasmids are of interest as these are often hard to disguish from contamination. Results will likely include  higher number of spurious annotations."
                            )
                        },
                        delayMillis = 100,
                        tooltipPlacement = TooltipPlacement.CursorPoint(alignment = Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                DropdownSelector(
                    modifier = Modifier.width(128.dp),
                    expanded = state.showCleaModeDropdown,
                    onDismissRequest = {
                        onIntent(PanarooParamsIntent.HideCleanModeDropdown)
                    },
                    selectedOption = state.cleanMode,
                    options = CleanMode.entries,
                    onClickOption = {
                        onIntent(PanarooParamsIntent.ChangeCleanMode(it))
                    },
                    onClick = {
                        onIntent(PanarooParamsIntent.ShowCleanModeDropdown)
                    }
                )

            }
            VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))
        }
        HorizontalDivider()
    }
}


@Preview
@Composable
private fun ConfigPreview() {
    GenomeTheme {
        Config(modifier = Modifier.fillMaxWidth().padding(16.dp), state = PanarooParams(maxThreads = 4)) {

        }
    }
}