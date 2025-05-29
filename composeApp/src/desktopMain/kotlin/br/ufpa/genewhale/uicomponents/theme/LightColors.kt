package br.ufpa.genewhale.uicomponents.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

object LightColors {
    val primary: Color = Color(0xFF00688F)
    val primaryContainer: Color = Color(0xFF1fb9f2)
    val inversePrimary: Color = Color(0xFF78d7fa)
    val secondary: Color = Color(0xFF668890)
    val secondaryContainer: Color = Color(0xFFBBBBBB)
    val surface: Color = Color(0xFFFFFFFF)
    val surfaceContainer: Color = Color(0xFFFFFFFF)
    val surfaceContainerLow: Color = Color(0xFFf5f5f5)
    val background: Color = Color(0xFFF0F0F0)
    val outlineVariant: Color = Color(0xFFd1d5db)
    val iconColor: Color = Color(0xFF4b5563)
}

val MyLightColorScheme: ColorScheme
    @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.copy(
        primary = LightColors.primary,
        primaryContainer = LightColors.primaryContainer,
        inversePrimary = LightColors.inversePrimary,
        secondary = LightColors.secondary,
        secondaryContainer = LightColors.secondaryContainer,
        surface = LightColors.surface,
        surfaceContainer = LightColors.surfaceContainer,
        surfaceContainerLow = LightColors.surfaceContainerLow,
        background = LightColors.background,
        outlineVariant = LightColors.outlineVariant
    )