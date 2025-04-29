package br.ufpa.genewhale.dropdown

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.clickableWithHoverIcon
import br.ufpa.genewhale.theme.GenomeTheme
import br.ufpa.genewhale.theme.ThemeDefaults

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T> DropdownSelector(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    selectedOption: T,
    options: List<T>,
    onClickOption: (T) -> Unit
) {
    Row(
        modifier = modifier.clip(ThemeDefaults.ButtonShape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier.Companion
                .clickableWithHoverIcon {
                    onClick()
                }
                .weight(1f)
                .height(24.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline, ThemeDefaults.ButtonShape)
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(selectedOption.toString(), fontSize = 12.sp, lineHeight = 12.sp)
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = onDismissRequest,
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                ) {
                    options.forEachIndexed { index, value ->
                        DropdownMenuItem(
                            text = { Text(value.toString()) },
                            onClick = {
                                onDismissRequest()
                                onClickOption(value)
                            },
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        )
                        if (index < options.size - 1) {
                            HorizontalDivider()
                        }
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
            onClick = {},
            onDismissRequest = {},
            options = listOf("Option 1", "Option 2", "Option 3"),
            onClickOption = {},
            selectedOption = "Option 1"
        )
    }
}