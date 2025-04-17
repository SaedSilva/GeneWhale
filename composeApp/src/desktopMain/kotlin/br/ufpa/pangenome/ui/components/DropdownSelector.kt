package br.ufpa.pangenome.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ThemeDefaults
import br.ufpa.pangenome.ui.states.tools.CleanMode

@Composable
fun <T> DropdownSelector(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    selectedOption: T,
    options: List<T>,
    onClickOption: (T) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(32.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary, ThemeDefaults.ButtonShape)
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(selectedOption.toString())
                DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(it.toString()) },
                            onClick = { onClickOption(it) }
                        )
                        HorizontalDivider()
                    }
                }
            }
            if (!expanded) {
                Icon(Icons.Outlined.KeyboardArrowDown, null, modifier = Modifier.align(Alignment.CenterEnd))
            } else {
                Icon(Icons.Outlined.KeyboardArrowUp, null, modifier = Modifier.align(Alignment.CenterEnd))
            }
        }
    }
}

@Preview
@Composable
private fun DropDownSelectorPreview() {
    GenomeTheme {
        DropdownSelector(
            modifier = Modifier.padding(16.dp),
            expanded = true,
            onDismissRequest = {},
            options = CleanMode.entries,
            onClickOption = {},
            selectedOption = CleanMode.NONE
        )
    }
}