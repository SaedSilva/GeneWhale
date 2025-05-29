package br.ufpa.genewhale.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.uicomponents.cards.ToolCard
import br.ufpa.genewhale.uicomponents.cards.Tools
import br.ufpa.genewhale.uicomponents.theme.GenomeTheme
import org.jetbrains.compose.resources.painterResource
import pangenome.composeapp.generated.resources.Res
import pangenome.composeapp.generated.resources.genewhalegenewhaleicon

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
        OutlinedCard {
            Column(
                modifier = Modifier
                    .widthIn(max = 1072.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.genewhalegenewhaleicon),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = "GeneWhale is an integrated bioinformatics tool that combines multiple pangenome analysis software into a unified platform. It simplifies and automates complex workflows by running all analyses within a pre-configured Docker environment, ensuring reproducibility, portability, and ease of use across different systems.",
                    modifier = Modifier,
                    textAlign = TextAlign.Justify,
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                )
            }
        }

        Text("Tools", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        LazyVerticalGrid(
            modifier = Modifier.widthIn(max = 1072.dp),
            columns = GridCells.FixedSize(256.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ToolCard(
                    tool = Tools.Panaroo,
                    onClick = { onNavigateToPanarooScreen() }
                )
            }
            //TODO add the rest of the tools
            /*item {
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
            }*/
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