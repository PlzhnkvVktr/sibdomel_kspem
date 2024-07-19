package ru.avem.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.avem.components.ActionButton
import ru.avem.components.Header
import ru.avem.components.UserListItem
import ru.avem.viewmodels.SettingsScreenViewModel
import ru.avem.components.ScrollableLazyColumn

class SettingsScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { SettingsScreenViewModel() }
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

        LifecycleEffect(onStarted = { viewModel.getUsers() })

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { Header() },
            content = {
                Column (
                    modifier = Modifier.fillMaxSize().padding(50.dp)
                ) {
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
                                        text = "Введите логин",
                                        modifier = Modifier.fillMaxSize(),
                                        style = MaterialTheme.typography.h5,
                                    )
                                },
                                onValueChange = {
                                    viewModel.textFind.value = it
                                    viewModel.performSearch(it)
                                }
                            )
                            ActionButton("Сохранить", Icons.Default.Add){
                                viewModel.openAlertDialog.value = true
                            }
                        }
                        if (viewModel.userList.isNotEmpty()) {
                            ScrollableLazyColumn {
                                items(viewModel.userList.size) {
                                    UserListItem(viewModel.userList[it],
                                        {
                                            user -> viewModel.currentUser.value = viewModel.userList[it]
                                            viewModel.isAdminMode.value = true
                                        }
                                    ) {user ->
                                        viewModel.deleteUser(viewModel.userList[it])
                                    }
                                }
                            }
                        } else {
                            Row (Modifier.fillMaxSize().padding(top = 50.dp), horizontalArrangement = Arrangement.Center){
                                Text("Пользователь не найдет", style = MaterialTheme.typography.h3)
                            }

                        }
                    }
                }
                if (viewModel.openAlertDialog.value) {
                    Dialog(viewModel)
                }
                if (viewModel.isAdminMode.value) {
                    UpdateUserDialog(viewModel)
                }
            },
            bottomBar = {  }
        )
    }
}

@Composable
fun Dialog(
    viewModel: SettingsScreenViewModel,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Добавьте нового пользователя",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h4
                )
                OutlinedTextField(
                    value = viewModel.login.value,
                    modifier = Modifier.fillMaxWidth(.9f),
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Логин") },
                    placeholder =  {
                        Text(
                            text = "Введите логин",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        ) },
                    onValueChange = {viewModel.login.value = it}
                )
                if (viewModel.loginError.value != "") {
                    Text(viewModel.loginError.value, color = Color.Red)
                }
                OutlinedTextField(
                    value = viewModel.password.value,
                    modifier = Modifier.fillMaxWidth(.9f),
                    onValueChange = { viewModel.password.value = it },
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Пароль") },
                    singleLine = true,
                    placeholder =  {
                        Text(
                            text = "Введите логин",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        ) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Показать" else "Спрятать"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = {
                            viewModel.addUser()
//                            viewModel.clearFields()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Сохранить",
                            style = MaterialTheme.typography.h4
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.openAlertDialog.value = false
                            viewModel.clearFields()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Отмена",
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateUserDialog(
    viewModel: SettingsScreenViewModel,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    viewModel.login.value = viewModel.currentUser.value!!.login
    viewModel.password.value = viewModel.currentUser.value!!.password
    viewModel.switchOn.value = viewModel.currentUser.value!!.isAdmin
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Редактировать",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h4
                )
                OutlinedTextField(
                    value = viewModel.login.value,
                    modifier = Modifier.fillMaxWidth(.9f),
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Логин") },
                    placeholder =  {
                        Text(
                            text = "Введите логин",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        ) },
                    onValueChange = {viewModel.login.value = it}
                )
                OutlinedTextField(
                    value = viewModel.password.value,
                    modifier = Modifier.fillMaxWidth(.9f),
                    onValueChange = { viewModel.password.value = it },
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Пароль") },
                    singleLine = true,
                    placeholder =  {
                        Text(
                            text = "Введите пароль",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        ) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Показать" else "Спрятать"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = {
                            viewModel.updateUser(viewModel.currentUser.value!!)
                            viewModel.isAdminMode.value = false
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Сохранить",
                            style = MaterialTheme.typography.h4
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.isAdminMode.value = false
                            viewModel.clearFields()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Отмена",
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}