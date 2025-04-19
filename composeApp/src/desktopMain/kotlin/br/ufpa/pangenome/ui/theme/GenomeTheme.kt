package br.ufpa.pangenome.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GenomeTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme.copy(
        primary = Colors.primary,
        secondaryContainer = Colors.secondaryContainer,
        surface = Colors.surface,
        surfaceContainer = Colors.surfaceContainer,
        background = Colors.background,
        outline = Colors.outline,
    ),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}
