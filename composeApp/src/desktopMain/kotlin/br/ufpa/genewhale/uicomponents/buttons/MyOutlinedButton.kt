package br.ufpa.genewhale.uicomponents.buttons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import br.ufpa.genewhale.uicomponents.theme.ThemeDefaults

@Composable
fun MyOutlinedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val otherModifier = if (enabled) Modifier.pointerHoverIcon(PointerIcon.Hand) else Modifier
    OutlinedButton(
        modifier = modifier.then(otherModifier),
        onClick = onClick,
        enabled = enabled,
        shape = ThemeDefaults.ButtonShape,
        content = content
    )
}