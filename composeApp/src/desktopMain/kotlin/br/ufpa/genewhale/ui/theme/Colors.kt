package br.ufpa.genewhale.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

object Colors {
    val primary: Color = Color(0xFF888888)
    val inversePrimary: Color = Color(0xFFFFFFFF)
    val secondaryContainer: Color = Color(0xFFBBBBBB)
    val surface: Color = Color(0xFFF0F0F0)
    val surfaceContainer: Color = Color(0xFFFFFFFF)
    val background: Color = Color(0xFFF0F0F0)
    val outline: Color = Color(0xFF888888)
}

val MyColorScheme: ColorScheme
    @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.copy(
        primary = Colors.primary,
        inversePrimary = Colors.inversePrimary,
        secondaryContainer = Colors.secondaryContainer,
        surface = Colors.surface,
        surfaceContainer = Colors.surfaceContainer,
        background = Colors.background,
        outline = Colors.outline,
    )