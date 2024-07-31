package ru.avem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.avem.viewmodels.EditorMarkSteelScreenViewModel
import ru.avem.viewmodels.EditorOIScreenViewModel

@Composable
fun CreatorMarkSteel (viewModel: EditorMarkSteelScreenViewModel) {

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ){
        Text(text = "Значения", style = MaterialTheme.typography.h4, modifier = Modifier.padding(20.dp))

        viewModel.marksSteelList.forEach { it->
            ParamOIRow(it)
        }
    }
}