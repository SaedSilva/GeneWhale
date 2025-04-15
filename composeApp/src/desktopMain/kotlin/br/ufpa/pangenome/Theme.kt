package br.ufpa.pangenome

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

object Colors {
    val primary: Color = Color(0xFF888888)
    val secondaryContainer: Color = Color(0xFFBBBBBB)
    val surface: Color = Color(0xFFF0F0F0)
    val background: Color = Color(0xFFF0F0F0)
    val outline: Color = Color(0xFF888888)
}

@Composable
fun GenomeTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme.copy(
        primary = Colors.primary,
        secondaryContainer = Colors.secondaryContainer,
        surface = Colors.surface,
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

object ThemeDefaults {
    val ButtonShape: CornerBasedShape
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes.small
}



