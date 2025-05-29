package br.ufpa.genewhale.uicomponents.sliders

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

@Composable
fun ThreadsSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    maxThreads: Int,
    threads: Int
) {
    Column(
        modifier = modifier
    ) {
        Text("Threads for exec", fontSize = 14.sp)
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
                    .height(24.dp),
                steps = maxThreads - 1
            )
            Text("$maxThreads", fontSize = 14.sp)
        }
        Text("Selected threads: $threads", fontSize = 14.sp)
    }
}
