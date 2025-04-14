package br.ufpa.pangenome.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
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
fun PickFolder(
    modifier: Modifier = Modifier,
    value: String,
    onChangeValue: (String) -> Unit,
    onClickClear: () -> Unit,
    buttonText: String,
    placeHolder: String,
    onClickButton: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(32.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary, ThemeDefaults.ButtonShape)
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onChangeValue(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )
            if (value.isBlank()) {
                Text(placeHolder, fontSize = 16.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                AnimatedVisibility(value.isNotBlank()) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp).clickable {
                            onClickClear()
                        }
                    )
                }
                Icon(
                    painterResource(Res.drawable.folder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp).clickable { onClickButton() }

                )
            }
        }

        /*OutlinedButton(
            onClick = {
                onClickButton()
            },
            shape = ThemeDefaults.ButtonShape
        ) {
            Text(buttonText)
        }*/
    }
}

@Preview
@Composable
private fun PickFolderPreview() {
    GenomeTheme {
        PickFolder(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onChangeValue = { },
            onClickClear = { },
            buttonText = "Select Input",
            placeHolder = "Select input",
            onClickButton = {}
        )
    }
}