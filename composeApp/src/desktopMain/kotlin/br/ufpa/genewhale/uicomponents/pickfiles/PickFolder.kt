package br.ufpa.genewhale.uicomponents.pickfiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufpa.genewhale.uicomponents.clickableWithHoverIcon
import br.ufpa.genewhale.uicomponents.theme.GenomeTheme
import br.ufpa.genewhale.uicomponents.theme.LightColors
import br.ufpa.genewhale.uicomponents.theme.ThemeDefaults
import br.ufpa.genewhale.uicomponents.tooltips.MyTooltip
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.compose.rememberDirectoryPickerLauncher
import java.awt.Window

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PickFolder(
    modifier: Modifier = Modifier,
    value: String,
    window: Window? = null,
    onChangeValue: (String) -> Unit,
    onClickClear: () -> Unit,
    placeHolder: String,
    onClickButton: () -> Unit,
    onResult: (PlatformFile?) -> Unit = {},
    tooltip: String,
    topText: String? = null,
    bottomText: String? = null
) {
    val launcher = rememberDirectoryPickerLauncher(
        dialogSettings = FileKitDialogSettings(window)
    ) { file ->
        onResult(file)
    }

    Column(
        modifier = modifier,
    ) {

        topText?.let {
            Text(it)
        }

        Box(
            modifier = Modifier
                .height(height)
                .border(1.dp, MaterialTheme.colorScheme.outline, ThemeDefaults.TextFieldShape),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onChangeValue(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(start = startEndPadding),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )
            if (value.isBlank()) {
                Text(
                    placeHolder,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(start = startEndPadding)
                )
            }

            TooltipArea(
                modifier = Modifier.align(Alignment.CenterEnd),
                tooltip = { MyTooltip(tooltip = tooltip) },
                delayMillis = 100,
                tooltipPlacement = TooltipPlacement.CursorPoint(alignment = Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .clickableWithHoverIcon {
                            onClickButton()
                            launcher.launch()
                        }
                        .background(
                            color = MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 4.dp,
                                bottomEnd = 4.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .height(height)
                        .width(height + 16.dp)

                ) {
                    Icon(
                        Icons.Outlined.FolderOpen,
                        tint = LightColors.iconColor,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        bottomText?.let {
            Text(it, color = LightColors.iconColor)
        }
    }
}

@Preview
@Composable
private fun PickFolderPreview() {
    GenomeTheme {
        PickFolder(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = "",
            onChangeValue = { },
            onClickClear = { },
            tooltip = "",
            placeHolder = "/path/to/folder",
            onClickButton = {},
            topText = "Teste",
            bottomText = "teste"
        )
    }
}

private val startEndPadding = 4.dp
private val height = 32.dp