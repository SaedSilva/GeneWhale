package br.ufpa.genewhale.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

object Colors {
    val primary: Color = Color(0xFF00688F)
    val inversePrimary: Color = Color(0xFF78d7fa)
    val secondary: Color = Color(0xFF668890)
    val secondaryContainer: Color = Color(0xFFBBBBBB)
    val surface: Color = Color(0xFFF0F0F0)
    val surfaceContainer: Color = Color(0xFFFFFFFF)
    val background: Color = Color(0xFFF0F0F0)
}

val MyColorScheme: ColorScheme
    @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.copy(
        primary = Colors.primary,
        inversePrimary = Colors.inversePrimary,
        secondary = Colors.secondary,
        secondaryContainer = Colors.secondaryContainer,
        surface = Colors.surface,
        surfaceContainer = Colors.surfaceContainer,
        background = Colors.background,
    )