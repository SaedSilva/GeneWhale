package br.ufpa.pangenome.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.pangenome.GenomeTheme
import br.ufpa.pangenome.ThemeDefaults
import org.jetbrains.compose.resources.painterResource
import pangenome.composeapp.generated.resources.Res
import pangenome.composeapp.generated.resources.folder

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChangeValue: (String) -> Unit
) {
    Box(
        modifier = modifier
            .height(24.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, ThemeDefaults.ButtonShape)
            .padding(4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = { onChangeValue(it) },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, lineHeight = 12.sp)
        )
    }

}

@Preview
@Composable
private fun CustomTextField() {
    GenomeTheme {
        CustomTextField(
            modifier = Modifier,
            value = "Test",
            onChangeValue = {}
        )
    }
}