package ru.avem.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.avem.components.*
import ru.avem.db.DBManager
import ru.avem.viewmodels.EditorMarkSteelScreenViewModel

class EditorMarkSteelScreen(): Screen {

    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { EditorMarkSteelScreenViewModel() }
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

        LifecycleEffect(
            onStarted = viewModel::initSteelMark
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
                        Text("Марки стали")
                        ComboBox(
                            viewModel.selectedSteelEditScreen,
                            modifier = Modifier.fillMaxWidth(),
                            items = viewModel.typesSteelEditScreen,
                            onClick = viewModel::initSteelMark
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                viewModel.addNewSteel.value = !viewModel.addNewSteel.value
                                if (viewModel.addNewSteel.value) viewModel.clearFields() else viewModel.initSteelMark()
                            }) {
                                Icon(
                                    if (!viewModel.addNewSteel.value) Icons.Filled.Add else Icons.Filled.ArrowBack,
                                    contentDescription = "Информация о приложении", modifier = Modifier.size(50.dp)
                                )
                            }
                            Button(onClick = {
                                viewModel.deleteSteelMark()
                                viewModel.openDialogAndUpdate("Объект испытания удален")
                            }, enabled = DBManager.getAllSteelMarks().size != 1) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Информация о приложении", modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight().border(2.dp, Color.DarkGray)
                    ){
                        CreatorMarkSteel(viewModel)
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        ActionButton(
                            if (!viewModel.addNewSteel.value) "Сохранить" else "Добавить",
                            Icons.Filled.Check,
                            disabled = viewModel.marksSteelList.none { it.isError.value }
                        ) {
                            viewModel.addNewSteelMark()
                            viewModel.openDialogAndUpdate(if (!viewModel.addNewSteel.value) "Тип стали сохранен" else "Тип стали добавлен")
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