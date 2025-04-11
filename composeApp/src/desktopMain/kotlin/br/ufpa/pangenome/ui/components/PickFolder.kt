package br.ufpa.pangenome.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ThemeDefaults

@Composable
fun PickFolder(
    modifier: Modifier = Modifier,
    value: String,
    onChangeValue: (String) -> Unit,
    onClickClear: () -> Unit,
    buttonText: String,
    onClickButton: () -> Unit
) {
    Row(
        modifier = modifier,
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
                value = value,
                onValueChange = { onChangeValue(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )
            if (value.isBlank()) {
                Text("Input folder...", fontSize = 16.sp)
            }
        }
        if (value.isNotBlank()) {
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.padding(8.dp).size(16.dp).clickable {
                    onClickClear()
                }
            )
        }
        OutlinedButton(
            onClick = {
                onClickButton()
            },
            shape = ThemeDefaults.ButtonShape
        ) {
            Text(buttonText)
        }
    }
}

@Preview
@Composable
private fun PickFolderPreview() {
    GenomeTheme {
        PickFolder(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onChangeValue = { },
            onClickClear = { },
            buttonText = "Select Input",
            onClickButton = {}
        )
    }
}