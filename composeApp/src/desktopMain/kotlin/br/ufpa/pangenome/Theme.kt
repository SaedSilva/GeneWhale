package br.ufpa.pangenome

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GenomeTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme.copy(
        primary = Color(0xFF666666),
        primaryContainer = Color(0xFF556666),
        background = Color(0xFFF0F0F0)
    ),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}



