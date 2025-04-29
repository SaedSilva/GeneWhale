package br.ufpa.genewhale.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GenomeTheme(
    colorScheme: ColorScheme = MyLightColorScheme,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}
