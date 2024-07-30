package ru.avem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.utils.kb
import ru.avem.viewmodels.MainScreenViewModel

@Composable
fun TestObjectCard (viewModel: MainScreenViewModel, factoryNumber: MutableState<String>, selectedTI: MutableState<String>, isVisible: MutableState<Boolean> = mutableStateOf(true)) {

    Card(
        modifier = Modifier
            .width(620.dp)
            .height(450.dp)
            .padding(
                top = 70.dp,
                start = 15.dp,
                end = 15.dp
            ),
        border = BorderStroke(2.dp, Color.Gray),
        elevation = 10.dp
    ) {
        if (isVisible.value) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.78f).fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(.5f)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Заводской номер",
                                style = MaterialTheme.typography.h5
                            )
                            OutlinedTextField(
                                value = factoryNumber.value,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .kb(),
                                textStyle = MaterialTheme.typography.h5,
                                placeholder = {
                                    Text(
                                        text = "Введите серийный номер",
                                        style = MaterialTheme.typography.h5
                                    )
                                              },
                                onValueChange = { factoryNumber.value = it }
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Тип",
                                style = MaterialTheme.typography.h5
                            )
                            ComboBox(selectedTI, modifier = Modifier.fillMaxWidth(0.8f).height(65.dp), items = viewModel.typesTI)
                        }

                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            isVisible.value = !isVisible.value
                        }
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Удалить",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }
        } else {
            Button(
                onClick = { isVisible.value = !isVisible.value }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Добавить",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}