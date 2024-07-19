package ru.avem.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.components.*
import ru.avem.viewmodels.MainScreenViewModel

class MainScreen() : Screen {
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { MainScreenViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

        LifecycleEffect(
            onStarted = viewModel::clearTestItemList
        )

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { Header() },
            content = {
                Column (
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Заполните все поля",  style = MaterialTheme.typography.h3)
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.width(200.dp),
                            text = "Заводской номер",
                            style = MaterialTheme.typography.h5,
                        )

                        OutlinedTextField(
                            value = viewModel.factoryNumber.value,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            textStyle = MaterialTheme.typography.h5,
                            placeholder =  {
                                Text(
                                    text = "Введите серийный номер",
                                    style = MaterialTheme.typography.h5,
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = Color.Red
                                ) },
                            onValueChange = {viewModel.factoryNumber.value = it}
                        )
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.width(200.dp),
                            text = "Тип",
                            style = MaterialTheme.typography.h5
                        )
                        ComboBox(viewModel.selectedTI, modifier = Modifier.fillMaxWidth(0.8f), items = viewModel.typesTI)
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (
                            modifier = Modifier
                                .width(200.dp)
                                .fillMaxHeight(.6f),
                            verticalArrangement = Arrangement.SpaceBetween
                        ){
                            ActionButton(
                                text = if (!viewModel.allCheckedButton.value) "Выбрать все" else "Отменить все",
                                pic = if (!viewModel.allCheckedButton.value) Icons.Filled.Check else Icons.Filled.Close,
                                onClick = viewModel::selectAll
                            )
                            ActionButton(
                                text = "Старт",
                                pic = Icons.Filled.PlayArrow,
                                disabled = (viewModel.startTestButton.value && viewModel.factoryNumber.value.isNotEmpty()),
                                onClick = { if (viewModel.testsLine.value.hasNext()) viewModel.startTests(navigator) }
                            )
                        }
                        TestListContainer(viewModel)
                    }
                }
            }
        )
    }
}