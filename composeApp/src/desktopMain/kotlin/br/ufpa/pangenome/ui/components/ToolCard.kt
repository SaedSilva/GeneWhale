package br.ufpa.pangenome.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import pangenome.composeapp.generated.resources.Res
import pangenome.composeapp.generated.resources.panaroo
import pangenome.composeapp.generated.resources.ppanggolin

@Composable
fun ToolCard(modifier: Modifier = Modifier, tool: Tools, onClick: () -> Unit) {
    val scrollState = rememberScrollState()

    OutlinedCard(modifier.width(256.dp).height(300.dp)) {
        Box(
            modifier = Modifier.clickable { onClick() }.padding(16.dp).fillMaxSize(),
        ) {
            tool.icon?.let {
                Image(
                    painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(256.dp).align(Alignment.Center),
                    alpha = 0.35f,
                )
            }
            Column {
                Text(
                    "${tool.title}\n${tool.version} - ${tool.versionDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    tool.description,
                    modifier = Modifier.verticalScroll(scrollState)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToolCardPreview() {
    GenomeTheme {
        ToolCard(
            modifier = Modifier.padding(16.dp),
            tool = Tools.Panaroo
        ) {

        }
    }
}

sealed class Tools(
    val title: String,
    val description: String,
    val version: String,
    val versionDate: String,
    val icon: DrawableResource?
) {
    data object Panaroo : Tools(
        title = "Panaroo",
        description = "An updated pipeline for pangenome investigation",
        version = "1.5.2",
        versionDate = "Mar 3, 2025",
        icon = Res.drawable.panaroo
    )

    data object Ppanggolin : Tools(
        title = "Ppanggolin",
        description = "PPanGGOLiN is a software suite used to create and manipulate prokaryotic pangenomes from a set of either genomic DNA sequences or provided genome annotations. It is designed to scale up to tens of thousands of genomes. It has the specificity to partition the pangenome using a statistical approach rather than using fixed thresholds which gives it the ability to work with low-quality data such as Metagenomic Assembled Genomes (MAGs) or Single-cell Amplified Genomes (SAGs) thus taking advantage of large scale environmental studies and letting users study the pangenome of uncultivable species.",
        version = "2.2.2",
        versionDate = "",
        icon = Res.drawable.ppanggolin
    )

    data object Roary : Tools(
        title = "Roary",
        description = "Roary is a high speed stand alone pan genome pipeline, which takes annotated assemblies in GFF3 format (produced by Prokka (Seemann, 2014)) and calculates the pan genome. Using a standard desktop PC, it can analyse datasets with thousands of samples, something which is computationally infeasible with existing methods, without compromising the quality of the results. 128 samples can be analysed in under 1 hour using 1 GB of RAM and a single processor. To perform this analysis using existing methods would take weeks and hundreds of GB of RAM. Roary is not intended for meta-genomics or for comparing extremely diverse sets of genomes.",
        version = "3.13.0",
        versionDate = "",
        icon = null
    )

    data object Splitmem : Tools(
        title = "SplitMEM",
        description = "With the rise of improved sequencing technologies, genomics is expanding from a single reference per species paradigm into a more comprehensive pan-genome approach with multiple individuals represented and analyzed together. Here we introduce a novel O(n log n) time and space algorithm called splitMEM, that directly constructs the compressed de Bruijn graph for a pan-genome of total length n. To achieve this time complexity, we augment the suffix tree with suffix skips, a new construct that allows us to traverse several suffix links in constant time, and use them to efficiently decompose maximal exact matches (MEMs) during a suffix tree traversal.",
        version = "1.0",
        versionDate = "",
        icon = null
    )

    data object Peppan : Tools(
        title = "PEPPAN",
        description = "a pipeline that can construct a pan-genome from thousands of genetically diversified bacterial genomes. PEPPAN implements a combination of tree- and synteny-based approaches to identify and exclude paralogous genes, as well as similarity-based gene predictions that support consistent annotations of genes and pseudogenes in individual genomes.",
        version = "1.0.5",
        versionDate = "",
        icon = null
    )

    data object Pirate : Tools(
        title = "PIRATE",
        description = "Cataloguing genes and their distributions within natural bacterial populations is essential for understanding evolutionary processes and the genetic bases of adaptation. genes that are shared between different bacterial strains and species is essential for understanding the genomic variation that underlies the enormous phenotypic variation observed in the microbial world. Here we present a pangenomics toolbox, PIRATE, which identifies and classifies orthologous gene families in bacterial pangenomes over a wide range of sequence similarity thresholds. PIRATE builds upon recent scalable software developments for the rapid interrogation of pangenomes from large dat thousands of genomes. PIRATE clusters genes (or other annotated features) over a wide range of amino-acid or nucleotide identity thresholds, and classifies paralogous genes families into either putative gene fission/fusion events or gene duplications. Furthermore, PIRATE provides a measure of allelic variance and cluster homology, and orders the resulting pangenome on a pangenome graph. Additional scripts are provided for comparison and visualization. PIRATE provides a robust framework for analysing the pangenomes of bacteria, from largely clonal to panmictic species.",
        version = "1.0.5",
        versionDate = "",
        icon = null
    )
}

val toolsList = listOf(
    Tools.Panaroo,
    Tools.Ppanggolin,
    Tools.Roary,
    Tools.Splitmem,
    Tools.Peppan,
    Tools.Pirate,
)