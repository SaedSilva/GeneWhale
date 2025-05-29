package br.ufpa.genewhale.uicomponents

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.uicomponents.theme.GenomeTheme
import br.ufpa.genewhale.uicomponents.theme.LightColors

@Composable
fun ToolScreen(
    modifier: Modifier = Modifier,
    title: String,
    onClickClose: (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colors.surface)
                .drawBehind {
                    val borderSize = 1.dp.toPx()
                    val y = size.height - borderSize / 2
                    drawLine(
                        color = LightColors.outlineVariant,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderSize,
                    )
                }
        ) {
            Text(
                title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            onClickClose?.let {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(32.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .clickableWithHoverIcon { it.invoke() }
                        .align(Alignment.CenterEnd)
                )
            }
        }
        content()
    }
}

@Preview
@Composable
private fun ToolScreenPreview() {
    GenomeTheme {
        ToolScreen(
            title = "Tool",
            onClickClose = null,
        ) {
            Terminal(
                output = listOf("Hello World")
            )
        }
    }
}