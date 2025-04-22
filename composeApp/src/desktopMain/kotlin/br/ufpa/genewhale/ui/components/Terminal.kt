package br.ufpa.genewhale.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.ufpa.genewhale.ui.theme.GenomeTheme
import br.ufpa.genewhale.ui.theme.ThemeDefaults

@Composable
fun Terminal(modifier: Modifier = Modifier, output: List<String>) {
    val state = rememberLazyListState()
    LaunchedEffect(output) {
        if (output.isNotEmpty()) {
            state.scrollToItem(output.size - 1)
        }
    }

    Column(
        modifier = modifier
            .clip(ThemeDefaults.ButtonShape)
            .background(Color.Black)
            .border(4.dp, MaterialTheme.colorScheme.primary)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Terminal",
                color = Color.Green,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Black)
                    .align(Alignment.TopEnd)
            )
            LazyColumn(modifier = Modifier.padding(8.dp), state) {
                items(output) {
                    TerminalLine(
                        modifier = Modifier.fillMaxWidth(),
                        text = it
                    )
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight().shadow(4.dp).padding(8.dp),
                style = LocalScrollbarStyle.current.copy(
                    hoverDurationMillis = 100,
                    hoverColor = Color.Green,
                    unhoverColor = Color.Green.copy(alpha = 0.5f)
                ),
                adapter = rememberScrollbarAdapter(
                    scrollState = state
                )
            )
        }

    }
}

@Composable
fun TerminalLine(modifier: Modifier, text: String) {
    Text(
        text = text,
        color = Color.White,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TerminalPreview() {
    GenomeTheme {
        Terminal(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            output = listOf(
                "docker run -it --rm -v /home/rafael/Downloads:/data papangenome/papangenome:latest",
                "docker run -it --rm -v /home/rafael/Downloads:/data papangenome/papangenome:latest",
                "docker run -it --rm -v /home/rafael/Downloads:/data papangenome/papangenome:latest",
                "docker run -it --rm -v /home/rafael/Downloads:/data papangenome/papangenome:latest"
            )
        )
    }
}

