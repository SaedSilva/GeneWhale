package br.ufpa.genewhale.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GenomeTheme(
    colorScheme: ColorScheme = MyColorScheme,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}
