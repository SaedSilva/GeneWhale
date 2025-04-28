package br.ufpa.genewhale.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.ufpa.genewhale.cards.ToolCard
import br.ufpa.genewhale.cards.Tools
import br.ufpa.genewhale.theme.GenomeTheme
import br.ufpa.genewhale.ui.states.HomeUIState
import br.ufpa.genewhale.ui.states.HomeUiIntent
import org.jetbrains.compose.resources.painterResource
import pangenome.composeapp.generated.resources.GeneWhaleNoBg
import pangenome.composeapp.generated.resources.Res

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUIState,
    onNavigateToPanarooScreen: () -> Unit,
    onIntent: (HomeUiIntent) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.GeneWhaleNoBg),
                contentDescription = null,
                modifier = Modifier.size(256.dp)
            )
            Text("GeneWhale is an integrated bioinformatics tool that combines multiple pangenome analysis software into a unified platform. It simplifies and automates complex workflows by running all analyses within a pre-configured Docker environment, ensuring reproducibility, portability, and ease of use across different systems.")
        }
        LazyVerticalGrid(
            columns = GridCells.FixedSize(256.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ToolCard(
                    tool = Tools.Panaroo,
                    onClick = {
                        onNavigateToPanarooScreen()
                    }
                )
            }
            item {
                ToolCard(
                    tool = Tools.Peppan,
                    onClick = {

                    },
                    enabled = false
                )
            }
            item {
                ToolCard(
                    tool = Tools.Pirate,
                    onClick = {

                    },
                    enabled = false
                )
            }
            item {
                ToolCard(
                    tool = Tools.Roary,
                    onClick = {

                    },
                    enabled = false
                )
            }
            item {
                ToolCard(
                    tool = Tools.Splitmem,
                    onClick = {

                    },
                    enabled = false
                )
            }
            item {
                ToolCard(
                    tool = Tools.Ppanggolin,
                    onClick = {

                    },
                    enabled = false
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    GenomeTheme {
        HomeScreen(modifier = Modifier.fillMaxSize(), state = HomeUIState(), {

        }) {
        }
    }
}