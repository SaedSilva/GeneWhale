package br.ufpa.pangenome.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ThemeDefaults

@Composable
fun MyTooltip(modifier: Modifier = Modifier, tooltip: String) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.75f), ThemeDefaults.ButtonShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(tooltip, color = Color.White)
    }
}

@Preview
@Composable
private fun MyTooltipPreview() {
    GenomeTheme {
        MyTooltip(
            tooltip = "This is a tooltip",
            modifier = Modifier.padding(8.dp)
        )
    }
}