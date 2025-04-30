package br.ufpa.genewhale.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.Terminal
import br.ufpa.genewhale.ToolScreen
import br.ufpa.genewhale.buttons.MyButton
import br.ufpa.genewhale.buttons.MyOutlinedButton
import br.ufpa.genewhale.pickfiles.PickFolder
import br.ufpa.genewhale.theme.GenomeTheme
import br.ufpa.genewhale.theme.ThemeDefaults
import io.github.vinceglb.filekit.path
import java.awt.Window

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Panaroo(
    modifier: Modifier = Modifier,
    state: PanarooUiState,
    window: Window? = null,
    onNavigateBack: () -> Unit,
    onNavigateToConfigure: () -> Unit,
    disposableEffect: () -> Unit = {},
    onIntent: (PanarooUiIntent) -> Unit,
) {
    ToolScreen(
        modifier = Modifier,
        title = "Panaroo",
        onClickClose = { onNavigateBack() },
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Terminal(modifier = Modifier.weight(0.75f), output = state.output)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
            ) {
                OutlinedCard(
                    shape = ThemeDefaults.ButtonShape
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Select folders", fontSize = 18.sp)
                        PickFolder(
                            modifier = Modifier,
                            value = state.inputFolder,
                            window = window,
                            onChangeValue = { onIntent(PanarooUiIntent.ChangeInputFolder(it)) },
                            onClickClear = { onIntent(PanarooUiIntent.ClearInputFolder) },
                            tooltip = "Select gff input folder with files",
                            placeHolder = "/path/to/input/folder",
                            onClickButton = { },
                            onResult = { it?.let { onIntent(PanarooUiIntent.ChangeInputFolder(it.path)) } }
                        )

                        PickFolder(
                            modifier = Modifier,
                            value = state.outputFolder,
                            window = window,
                            onChangeValue = { onIntent(PanarooUiIntent.ChangeOutputFolder(it)) },
                            onClickClear = { onIntent(PanarooUiIntent.ClearOutputFolder) },
                            tooltip = "Select output folder",
                            placeHolder = "/path/to/output/folder",
                            onClickButton = { },
                            onResult = { it?.let { onIntent(PanarooUiIntent.ChangeOutputFolder(it.path)) } }
                        )
                    }
                }

                OutlinedCard(
                    shape = ThemeDefaults.ButtonShape
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Controls", fontSize = 18.sp)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MyButton(
                                modifier = Modifier.height(32.dp),
                                onClick = {
                                    onIntent(PanarooUiIntent.ClearOutput)
                                    onIntent(PanarooUiIntent.RunPanaroo)
                                }
                            ) {
                                Text("Run Panaroo", lineHeight = 16.sp)
                            }
                            MyOutlinedButton(
                                modifier = Modifier
                                    .height(32.dp),
                                onClick = {
                                    onNavigateToConfigure()
                                }
                            ) {
                                Text(
                                    "Configure", lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            disposableEffect()
        }
    }
}

@Preview
@Composable
private fun PanarooPreview() {
    GenomeTheme {
        Panaroo(
            modifier = Modifier.fillMaxSize(),
            state = PanarooUiState(),
            onNavigateBack = {},
            onNavigateToConfigure = {},
        ) {}
    }
}

