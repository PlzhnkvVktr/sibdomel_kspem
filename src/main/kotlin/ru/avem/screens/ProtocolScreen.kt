package ru.avem.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.avem.components.Header
import ru.avem.components.ProtocolListItem
import ru.avem.viewmodels.ProtocolScreenViewModel
import ru.avem.components.ScrollableLazyColumn

class ProtocolScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { ProtocolScreenViewModel() }
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

        LifecycleEffect(onStarted = { viewModel.getProtocols() })

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { Header() },
            content = {
                Column {
                    Row(
                        modifier = Modifier.height(80.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = viewModel.textFind.value,
                            modifier = Modifier.fillMaxWidth(.5f).height(80.dp).padding(10.dp),
                            textStyle = MaterialTheme.typography.h5,
                            placeholder = {
                                Text(
                                    text = "Введите название",
                                    modifier = Modifier.fillMaxSize(),
                                    style = MaterialTheme.typography.h5,
                                    )
                                          },
                            onValueChange = {
                                viewModel.textFind.value = it
                                viewModel.performSearch(it)
                            }
                        )
                    }
                    if (viewModel.protocolList.isNotEmpty()) {
                        ScrollableLazyColumn {
                            items(viewModel.protocolList.size) {
                                ProtocolListItem(viewModel.protocolList[it],
//                                    {protocol -> viewModel.savePDF(protocol)},
                                    {protocol -> viewModel.saveExcel(protocol)}
                                ) {protocol ->
                                    viewModel.deleteProtocol(viewModel.protocolList[it])
                                }
                            }
                        }
                    } else {
                        Row (Modifier.fillMaxSize().padding(top = 50.dp), horizontalArrangement = Arrangement.Center){
                            Text("Протоколы не найдены", style = MaterialTheme.typography.h3)
                        }

                    }
                }
                      },
            bottomBar = { }
        )
    }
}