package br.ufpa.pangenome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.ufpa.pangenome.ui.components.SearchBar
import br.ufpa.pangenome.ui.states.ProjectUiIntent
import br.ufpa.pangenome.ui.states.ProjectUiState
import br.ufpa.pangenome.ui.theme.ThemeDefaults

@Composable
fun ProjectScreen(
    modifier: Modifier = Modifier,
    state: ProjectUiState,
    onIntent: (ProjectUiIntent) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchBar(
                modifier = Modifier.weight(1f),
                value = state.searchText,
                onChangeText = { onIntent(ProjectUiIntent.ChangeSearchText(it)) },
                placeHolder = "Search projects",
                onClickClear = { onIntent(ProjectUiIntent.ClearSearchText) }
            )
            Button(onClick = {}, shape = ThemeDefaults.ButtonShape) {
                Text("New project")
            }
        }
        HorizontalDivider()
    }
}