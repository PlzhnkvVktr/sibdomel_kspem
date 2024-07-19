package ru.avem.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.viewmodels.EditorOIScreenViewModel

@Composable
fun CreatorOI (viewModel: EditorOIScreenViewModel) {

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ){
        Text(text = "Значения", style = MaterialTheme.typography.h4, modifier = Modifier.padding(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.9f).height(120.dp).padding(start = 45.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Схема", style = MaterialTheme.typography.h5)
            Column (
                modifier = Modifier
            ) {
                Row {
                    Text("∆", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp, start = 10.dp))
                    Switch(
                        checked = viewModel.scheme.value,
                        onCheckedChange = {
                            viewModel.scheme.value = it
                        }
                    )
                    Text("λ", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp))
                }

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = "Тип", style = MaterialTheme.typography.h5)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                OutlinedTextField(
                    value = viewModel.name.value,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    textStyle = MaterialTheme.typography.body1,
                    onValueChange = {
                        viewModel.name.value = it
                                    }
                    )
            }
        }

        viewModel.parapmsList.forEach { it->
            ParamOIRow(it)
        }

    }
}