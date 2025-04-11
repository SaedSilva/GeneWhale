package br.ufpa.pangenome.ui.screens.tools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ThemeDefaults
import br.ufpa.pangenome.ui.components.MyTab
import br.ufpa.pangenome.ui.components.PickFolder
import br.ufpa.pangenome.ui.components.Terminal
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

    Column(modifier = modifier) {
        Row(modifier = Modifier.clickable {
            onNavigateBack()
        }, verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Default.ArrowBack, null)
            Text("Back", fontSize = 18.sp)
        }

        PickFolder(
            modifier = Modifier.fillMaxWidth(),
            value = state.inputFolder,
            onChangeValue = { onIntent(PanarooUiIntent.ChangeOutputFolder(it)) },
            onClickClear = { onIntent(PanarooUiIntent.ClearInputFolder) },
            buttonText = "Select Input",
            onClickButton = { inputLaucher.launch() }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, ThemeDefaults.ButtonShape)
                    .padding(4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = state.outputFolder,
                    onValueChange = { onIntent(PanarooUiIntent.ChangeOutputFolder(it)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )
                if (state.outputFolder.isBlank()) {
                    Text("Output folder...", fontSize = 16.sp)
                }
            }
            if (state.outputFolder.isNotBlank()) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp).size(16.dp).clickable {
                        onIntent(PanarooUiIntent.ClearOutputFolder)
                    }
                )
            }
            OutlinedButton(onClick = {
                outputLaucher.launch()
            }, shape = ThemeDefaults.ButtonShape) {
                Text("Select output")
            }
        }

        OutlinedButton(
            onClick = {
                onIntent(PanarooUiIntent.ClearOutput)
                onIntent(PanarooUiIntent.RunPanaroo)
            },
            shape = ThemeDefaults.ButtonShape
        ) {
            Text("Run Panaroo")
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
                    Text("Other")
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