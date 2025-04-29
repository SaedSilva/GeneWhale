package br.ufpa.genewhale.sliders

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.theme.GenomeTheme

@Composable
fun MemorySlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    maxMemory: Long,
    selectedMemory: Long
) {
    Column(
        modifier = modifier
    ) {
        Text("Memory for container", fontSize = 14.sp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text("0", fontSize = 14.sp)
            Slider(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand)
                    .weight(1f)
                    .height(24.dp)
            )
            Text("$maxMemory", fontSize = 14.sp)
        }
        val text = if (selectedMemory == 0L) "Selected memory: Auto" else "Selected memory: $selectedMemory mb"
        Text(
            text,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun MemorySliderPreview() {
    GenomeTheme {
        MemorySlider(
            value = 0.5f,
            onValueChange = {},
            maxMemory = 16L,
            selectedMemory = 8L
        )
    }
}
