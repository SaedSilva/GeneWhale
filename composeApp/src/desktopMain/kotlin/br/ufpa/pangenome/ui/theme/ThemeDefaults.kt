package br.ufpa.pangenome.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object ThemeDefaults {
    val ButtonShape: CornerBasedShape
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes.small
}
