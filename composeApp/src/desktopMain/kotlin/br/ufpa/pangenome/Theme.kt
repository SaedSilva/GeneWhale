package br.ufpa.pangenome

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

@Composable
fun GenomeTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme.copy(
        primary = Color(0xFF888888),
        secondaryContainer = Color(0xFFBBBBBB),
        surface = Color(0xFFF0F0F0),
        background = Color(0xFFF0F0F0)
    ),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}

object ThemeDefaults {
    val ButtonShape: CornerBasedShape
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes.small
}



