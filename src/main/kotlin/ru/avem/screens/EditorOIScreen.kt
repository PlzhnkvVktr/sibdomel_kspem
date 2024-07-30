package ru.avem.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.avem.components.*
import ru.avem.db.DBManager.getAllTI
import ru.avem.viewmodels.EditorOIScreenViewModel

class EditorOIScreen() : Screen {

    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { EditorOIScreenViewModel() }
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

        LifecycleEffect(
            onStarted = viewModel::initTestTI
        )

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { Header() },
            content = {
                Row(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.2f).fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Объект испытания")
                        ComboBox(
                            viewModel.selectedTI,
                            modifier = Modifier.fillMaxWidth(),
                            items = viewModel.typesTI,
                            onClick = viewModel::initTestTI
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                viewModel.addNewTI.value = !viewModel.addNewTI.value
                                if (viewModel.addNewTI.value) viewModel.clearFields() else viewModel.initTestTI()
                            }) {
                                Icon(
                                    if (!viewModel.addNewTI.value) Icons.Filled.Add else Icons.Filled.ArrowBack,
                                    contentDescription = "Информация о приложении", modifier = Modifier.size(50.dp)
                                )
                            }
                            Button(onClick = {
                                viewModel.deleteTI()
                                viewModel.openDialogAndUpdate("Объект испытания удален")
                            }, enabled = getAllTI().size != 1) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Информация о приложении", modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight().border(2.dp, Color.DarkGray)
                    ){
                        CreatorOI(viewModel)
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.35f).fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        ActionButton(
                            if (!viewModel.addNewTI.value) "Сохранить" else "Добавить",
                            Icons.Filled.Check,
                            disabled = viewModel.paramsList.none { it.isError.value }
                        ) {
                            viewModel.addNewTI()
                            viewModel.openDialogAndUpdate(if (!viewModel.addNewTI.value) "Объект испытания сохранен" else "Объект испытания добавлен")
                            }
                        }
                    }
                    if (viewModel.dialogWindow.value) {
                        TestDialog(viewModel.dialogText.value)
                    }
                },
                bottomBar = {  }
            )
    }
}
