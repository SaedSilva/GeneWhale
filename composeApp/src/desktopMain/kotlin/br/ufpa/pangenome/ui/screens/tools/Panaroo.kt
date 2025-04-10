package br.ufpa.pangenome.ui.screens.tools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ui.states.tools.PanarooUiIntent
import br.ufpa.pangenome.ui.states.tools.PanarooUiState
import io.github.vinceglb.filekit.dialogs.compose.rememberDirectoryPickerLauncher
import io.github.vinceglb.filekit.path

@Composable
fun Panaroo(
    modifier: Modifier = Modifier,
    state: PanarooUiState,
    onIntent: (PanarooUiIntent) -> Unit
) {

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
        Text("Input", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = state.inputFolder,
                    onValueChange = { onIntent(PanarooUiIntent.ChangeInputFolder(it)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )
                if (state.inputFolder.isBlank()) {
                    Text("Input folder...", fontSize = 16.sp)
                }
            }
            if (state.inputFolder.isNotBlank()) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp).size(16.dp).clickable {
                        onIntent(PanarooUiIntent.ClearInputFolder)
                    }
                )
            }
            OutlinedButton(onClick = {
                inputLaucher.launch()
            }, shape = MaterialTheme.shapes.small) {
                Text("Select input")
            }
        }

        Text("Output", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
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
                    Text("Input folder...", fontSize = 16.sp)
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
            }, shape = MaterialTheme.shapes.small) {
                Text("Select output")
            }
        }
    }
}

@Preview
@Composable
private fun PanarooPreview() {
    GenomeTheme {
        Panaroo(modifier = Modifier.fillMaxSize(), state = PanarooUiState()) {}
    }
}