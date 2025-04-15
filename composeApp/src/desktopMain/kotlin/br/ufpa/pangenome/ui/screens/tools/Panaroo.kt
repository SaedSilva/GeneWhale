package br.ufpa.pangenome.ui.screens.tools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import br.ufpa.pangenome.ui.states.tools.PanarooConfig
import br.ufpa.pangenome.ui.states.tools.PanarooConfigIntent
import br.ufpa.pangenome.ui.states.tools.PanarooUiIntent
import br.ufpa.pangenome.ui.states.tools.PanarooUiState
import io.github.vinceglb.filekit.dialogs.compose.rememberDirectoryPickerLauncher
import io.github.vinceglb.filekit.path

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Panaroo(
    modifier: Modifier = Modifier,
    state: PanarooUiState,
    onNavigateBack: () -> Unit,
    onIntent: (PanarooUiIntent) -> Unit
) {
    var index by remember { mutableStateOf(0) }

    val inputLaucher = rememberDirectoryPickerLauncher { directory ->
        directory?.let {
            onIntent(PanarooUiIntent.ChangeInputFolder(it.path))
        }
    }
    val outputLaucher = rememberDirectoryPickerLauncher { directory ->
        directory?.let {
            onIntent(PanarooUiIntent.ChangeOutputFolder(it.path))
        }
    }

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
            buttonText = "Select input",
            placeHolder = "Select input...",
            onClickButton = { inputLaucher.launch() }
        )

        PickFolder(
            modifier = Modifier.fillMaxWidth(),
            value = state.outputFolder,
            onChangeValue = { onIntent(PanarooUiIntent.ChangeOutputFolder(it)) },
            onClickClear = { onIntent(PanarooUiIntent.ClearOutputFolder) },
            buttonText = "Select output",
            placeHolder = "Select output...",
            onClickButton = { outputLaucher.launch() }
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
                        state = state.config,
                        onIntent = {
                            onIntent(PanarooUiIntent.ConfigIntent(it))
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
        Panaroo(modifier = Modifier.fillMaxSize(), state = PanarooUiState(), onNavigateBack = {}) {}
    }
}

@Composable
private fun Config(modifier: Modifier = Modifier, state: PanarooConfig, onIntent: (PanarooConfigIntent) -> Unit) {
    Column(modifier = modifier) {
        Row {
            MemorySlider(
                modifier = Modifier.weight(1f),
                value = state.memorySlider,
                onValueChange = {
                    onIntent(PanarooConfigIntent.ChangeMemorySlider(it))
                },
                maxMemory = state.maxMemory,
                selectedMemory = state.memory,
            )
            VerticalDivider(modifier = Modifier.height(72.dp).padding(end = 4.dp, start = 4.dp))
            ThreadsSlider(
                modifier = Modifier.weight(1f),
                value = state.threadsSlider,
                onValueChange = { onIntent(PanarooConfigIntent.ChangeThreadsSlider(it)) },
                maxThreads = state.maxThreads,
                threads = state.threads
            )
        }
        HorizontalDivider()
    }
}


@Preview
@Composable
private fun ConfigPreview() {
    GenomeTheme {
        Config(modifier = Modifier.fillMaxSize().padding(16.dp), state = PanarooConfig(memorySlider = 0.5f)) {

        }
    }
}