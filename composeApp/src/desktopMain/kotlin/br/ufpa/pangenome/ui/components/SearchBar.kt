package br.ufpa.pangenome.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onChangeText: (String) -> Unit,
    onClickClear: () -> Unit,
    placeHolder: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(24.dp))
        Box(modifier = Modifier.weight(1f).heightIn(min = 24.dp), contentAlignment = Alignment.CenterStart) {
            BasicTextField(
                value = value,
                onValueChange = { onChangeText(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            if (value.isBlank()) {
                Text(placeHolder, fontSize = 12.sp)
            }
        }
        if (value.isNotBlank()) {
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.padding(8.dp).size(16.dp).clickable { onClickClear() }
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    GenomeTheme {
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = "",
            onChangeText = {

            },
            onClickClear = {},
            placeHolder = "Search projects"
        )
    }
}