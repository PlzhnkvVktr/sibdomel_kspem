package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.avem.common.repos.AppConfig
import ru.avem.db.DBManager
import ru.avem.db.User
import ru.avem.screens.MainScreen

class AuthScreenViewModel: ScreenModel {
    private val scope = CoroutineScope(Dispatchers.Default)
    var users: List<User> = DBManager.getAllUsers().ifEmpty { listOf() }
    var selectedUser = mutableStateOf(users.first())

    var login: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")
    var isError: MutableState<Boolean> = mutableStateOf(false)

    var passwordVisible = mutableStateOf(false)

    fun signIn (navigator: Navigator) {
        scope.launch {
            if (password.value == selectedUser.value.password) {
                AppConfig.update(selectedUser.value)
                navigator.replace(MainScreen())
            } else {
                isError.value = true
                password.value = ""
            }
        }
    }

}