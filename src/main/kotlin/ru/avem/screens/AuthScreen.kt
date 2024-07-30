package ru.avem.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.components.ActionButton
import ru.avem.components.ComboBox
import ru.avem.utils.kb
import ru.avem.viewmodels.AuthScreenViewModel

class AuthScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { AuthScreenViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

        LifecycleEffect(onStarted = { viewModel.isError.value = false })

        Scaffold(
            scaffoldState = scaffoldState,
            content = {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card (
                        modifier = Modifier.fillMaxSize(0.5f),
                        elevation = 10.dp
                    ) {
                        Column (
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(.7f),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column (
                                    modifier = Modifier.fillMaxHeight(.4f),
                                    verticalArrangement = Arrangement.SpaceAround
                                ) {
                                    Text("Оператор", style = MaterialTheme.typography.h3)
                                    Text(
                                        modifier = Modifier.width(200.dp),
                                        text = "Пароль",
                                        style = MaterialTheme.typography.h3,
                                        )
                                }
                                Column (
                                    modifier = Modifier.fillMaxHeight(.4f),
                                    verticalArrangement = Arrangement.SpaceAround
                                ) {
                                    ComboBox(
                                        viewModel.selectedUser,
                                        modifier = Modifier.fillMaxWidth(.9f),
                                        items = viewModel.users
                                    )
                                    OutlinedTextField(
                                        value = viewModel.password.value,
                                        modifier = Modifier
                                            .fillMaxWidth(.9f)

                                            .kb(),
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
                                        visualTransformation = if (viewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                                        trailingIcon = {
                                            val image = if (viewModel.passwordVisible.value)
                                                Icons.Filled.Visibility
                                            else Icons.Filled.VisibilityOff

                                            val description = if (viewModel.passwordVisible.value) "Hide password" else "Show password"

                                            IconButton(onClick = {viewModel.passwordVisible.value = !viewModel.passwordVisible.value}){
                                                Icon(imageVector  = image, description)
                                            }
                                        }
                                    )
                                }
                            }
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ActionButton(
                                    text = "Вход",
                                    pic = Icons.Sharp.Logout,
                                    disabled = viewModel.password.value != ""
                                ){
                                    viewModel.signIn(navigator)
                                }
                            }
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(if (viewModel.isError.value) "Неправильный логин или пароль" else "", color = Color.Red, style = MaterialTheme.typography.h4)
                            }
                        }
                    }
                }
            },
        )
    }
}