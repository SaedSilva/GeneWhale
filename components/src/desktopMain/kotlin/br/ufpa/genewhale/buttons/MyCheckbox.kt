package br.ufpa.genewhale.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.ufpa.genewhale.clickableWithHoverIcon
import br.ufpa.genewhale.theme.GenomeTheme

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val onPrimaryColor = if (checked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
    val primaryColor = MaterialTheme.colorScheme.primary
    val animatedColor by animateColorAsState(
        if (checked) MaterialTheme.colorScheme.secondary else primaryColor,
        label = "color"
    )

    Box(
        modifier = modifier
            .border(1.dp, onPrimaryColor, RoundedCornerShape(5.dp))
            .height(20.dp)
            .width(20.dp)
            .drawBehind {
                val cornerRadius =
                    5.dp.toPx() // must match the RoundedCornerShape(5.dp)
                drawRoundRect(
                    color = animatedColor,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
            .clip(
                RoundedCornerShape(5.dp)
            )
            .clickableWithHoverIcon {
                onCheckedChange(!checked)
            }
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            AnimatedVisibility(
                checked,
                enter = scaleIn(initialScale = 0.5f), // Scale in animation
                exit = shrinkOut(shrinkTowards = Alignment.Center)
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "checked",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}

@Composable
fun RoundedCornerCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    tintColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .border(width = 2.dp, color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .clip(CircleShape)
            .background(tintColor)
            .clickableWithHoverIcon { onCheckedChange(!checked) }
            .background(if (checked) backgroundColor else Color.White),
    ) {
        AnimatedVisibility(
            checked,
            enter = scaleIn(initialScale = 0.5f), // Scale in animation
            exit = shrinkOut(shrinkTowards = Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
            )
        }

    }
}


@Preview
@Composable
private fun CustomCheckBoxPreview() {
    GenomeTheme {
        Column {
            CustomCheckBox(
                checked = true,
                onCheckedChange = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
            RoundedCornerCheckBox(
                checked = true,
                onCheckedChange = {},
                tintColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}





