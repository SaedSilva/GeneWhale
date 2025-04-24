package br.ufpa.genewhale.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role

fun Modifier.clickableWithHoverIcon(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier {
    val otherModifier = if (enabled) Modifier.pointerHoverIcon(PointerIcon.Hand) else Modifier
    return this
        .then(otherModifier)
        .clickable(enabled, onClickLabel, role) { onClick() }
}