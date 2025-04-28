package br.ufpa.genewhale.ui.screens.tools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.Terminal
import br.ufpa.genewhale.buttons.MyButton
import br.ufpa.genewhale.buttons.MyOutlinedButton
import br.ufpa.genewhale.buttons.MyTab
import br.ufpa.genewhale.buttons.RoundedCornerCheckBox
import br.ufpa.genewhale.clickableWithHoverIcon
import br.ufpa.genewhale.dropdown.DropdownSelector
import br.ufpa.genewhale.pickfiles.PickFolder
import br.ufpa.genewhale.sliders.MemorySlider
import br.ufpa.genewhale.sliders.ThreadsSlider
import br.ufpa.genewhale.textfield.CustomTextField
import br.ufpa.genewhale.tooltips.MyTooltip
import br.ufpa.genewhale.ui.states.tools.*
import br.ufpa.genewhale.ui.theme.GenomeTheme
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
            Text(
                "Panaroo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            MyOutlinedButton(
                modifier = Modifier.align(Alignment.TopStart).height(32.dp),
                onClick = {
                    onIntent(PanarooUiIntent.OpenDocs)
                }
            ) {
                Text("📚 Open documentation", lineHeight = 16.sp)
            }
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .clickableWithHoverIcon { onNavigateBack() }
                    .align(Alignment.TopEnd)
            )
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


        MyOutlinedButton(
            modifier = Modifier.height(32.dp),
            onClick = {
                onIntent(PanarooUiIntent.ClearOutput)
                onIntent(PanarooUiIntent.RunPanaroo)
            }
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
                        modifier = Modifier.fillMaxSize(),
                        state = configState,
                        onIntent = { onConfigIntent(it) }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Config(modifier: Modifier = Modifier, state: PanarooParams, onIntent: (PanarooParamsIntent) -> Unit) {
    Box(
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
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

                //Clean Mode
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
                                            "Does not delete any genes and only performes merge and refinding operations. Useful if rare plasmids are of interest as these are often hard to disguish from contamination.\n" +
                                            "Results will likely include  higher number of spurious annotations."
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
                        onDismissRequest = { onIntent(PanarooParamsIntent.HideCleanModeDropdown) },
                        selectedOption = state.cleanMode,
                        options = CleanMode.entries,
                        onClickOption = { onIntent(PanarooParamsIntent.ChangeCleanMode(it)) },
                        onClick = { onIntent(PanarooParamsIntent.ShowCleanModeDropdown) }
                    )
                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //Remove invalid genes
                Column(
                    modifier = Modifier.height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                    ) {
                        RoundedCornerCheckBox(
                            checked = state.removeInvalidGenes,
                            onCheckedChange = { onIntent(PanarooParamsIntent.SetRemoveInvalidGenes(it)) }
                        )
                        Text("Remove invalid genes")
                        TooltipArea(
                            tooltip = {
                                MyTooltip(
                                    tooltip = "removes annotations that do not conform to the\n" +
                                            "expected Prokka format such as those including\n" +
                                            "premature stop codons."
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.height(56.dp)
                    ) {
                        RoundedCornerCheckBox(
                            checked = state.mergeParalogs,
                            onCheckedChange = { onIntent(PanarooParamsIntent.SetMergeParalogs(it)) }
                        )
                        Text("Merge paralogs")
                        TooltipArea(
                            tooltip = { MyTooltip(tooltip = "don't split paralogs") },
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


                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //threshold
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Threshold")
                        TooltipArea(
                            tooltip = { MyTooltip(tooltip = "sequence identity threshold (default=0.98)") },
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
                    CustomTextField(
                        modifier = Modifier,
                        value = state.threshold,
                        onChangeValue = { onIntent(PanarooParamsIntent.ChangeThreshold(it)) }
                    )
                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //family threshold
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Family threshold")
                        TooltipArea(
                            tooltip = { MyTooltip(tooltip = "protein family sequence identity threshold (default=0.7)") },
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
                    CustomTextField(
                        modifier = Modifier,
                        value = state.familyThreshold,
                        onChangeValue = { onIntent(PanarooParamsIntent.ChangeFamilyThreshold(it)) }
                    )
                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //lenDifPercent
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Len dif percent")
                        TooltipArea(
                            tooltip = { MyTooltip(tooltip = "length difference cutoff (default=0.98)") },
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
                    CustomTextField(
                        modifier = Modifier,
                        value = state.lenDifPercent,
                        onChangeValue = { onIntent(PanarooParamsIntent.ChangeLenDifPercent(it)) }
                    )
                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //familyLenDifPercent
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Family len dif percent")
                        TooltipArea(
                            tooltip = {
                                MyTooltip(tooltip = "length difference cutoff at the gene family level (default=0.0)")
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
                    CustomTextField(
                        modifier = Modifier,
                        value = state.familyLenDifPercent,
                        onChangeValue = { onIntent(PanarooParamsIntent.ChangeFamilyLenDifPercent(it)) }
                    )
                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //searchRadius
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Search radius")
                        TooltipArea(
                            tooltip = {
                                MyTooltip(
                                    tooltip = "the distance in nucleotides surronding the neighbour \n" +
                                            "of an accessory gene in which to search for it"
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
                    CustomTextField(
                        modifier = Modifier,
                        value = state.searchRadius,
                        onChangeValue = { onIntent(PanarooParamsIntent.ChangeSearchRadius(it)) }
                    )
                }
                VerticalDivider(modifier = Modifier.height(56.dp).padding(end = 4.dp, start = 4.dp))

                //refind prop match
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Refind prop match")
                        TooltipArea(
                            tooltip = {
                                MyTooltip(
                                    tooltip = "the proportion of an accessory gene that must be found\n" +
                                            "in order to consider it a match"
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
                    CustomTextField(
                        modifier = Modifier,
                        value = state.refindPropMatch,
                        onChangeValue = { onIntent(PanarooParamsIntent.ChangeRefindPropMatch(it)) }
                    )
                }
            }
            HorizontalDivider()

            Row {
                // refindMode
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Refind mode")
                        TooltipArea(
                            tooltip = {
                                MyTooltip(
                                    tooltip = "The stringency mode at which to re-find genes.\n" +
                                            "default: Will re-find similar gene sequences. Allows for premature stop codons and incorrect lengths to account for misassemblies.\n" +
                                            "strict: Prevents fragmented, misassembled, or potential pseudogene sequences from being re-found.\n" +
                                            "off: Turns off all re-finding steps."
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
                        expanded = state.showRefindModeDropdown,
                        onDismissRequest = { onIntent(PanarooParamsIntent.HideRefindModeDropdown) },
                        selectedOption = state.refindMode,
                        options = RefindMode.entries,
                        onClickOption = { onIntent(PanarooParamsIntent.ChangeRefindMode(it)) },
                        onClick = { onIntent(PanarooParamsIntent.ShowRefindModeDropdown) }
                    )
                }
            }
        }
        MyButton(
            modifier = Modifier.height(32.dp).align(Alignment.BottomEnd),
            onClick = {
                onIntent(PanarooParamsIntent.Save)
            }
        ) {
            Text("Save", lineHeight = 16.sp)
        }
    }
}


@Preview
@Composable
private fun ConfigPreview() {
    GenomeTheme {
        Config(modifier = Modifier.fillMaxSize().padding(16.dp), state = PanarooParams(maxThreads = 4)) {

        }
    }
}