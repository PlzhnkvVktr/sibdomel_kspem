package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.avem.db.DBManager
import ru.avem.db.User

class SettingsScreenViewModel : ScreenModel {
    private val scope = CoroutineScope(Dispatchers.Default)
    var userList = mutableStateListOf<User>()
    var textFind = mutableStateOf("")
    val openAlertDialog = mutableStateOf(false)
    var switchOn = mutableStateOf(false)
    var isAdminMode = mutableStateOf(false)
    var currentUser: MutableState<User?> = mutableStateOf(null)

    var login = mutableStateOf("")
    var loginError = mutableStateOf("")

    var password = mutableStateOf("")

    fun clearFields() {
        login.value = ""
        loginError.value = ""
        password.value = ""
        switchOn.value = false
    }
    fun getUsers() {
        scope.launch {
            userList.clear()
            userList.addAll(DBManager.getAllUsers())
        }
    }
    fun performSearch(predicate: String) {
        scope.launch {
            val values = DBManager.findUser(predicate)
            userList.clear()
            userList.addAll(values)
        }
    }

    fun addUser() {
        scope.launch {
            if (DBManager.findUserLogin(login.value)) {
                loginError.value = "Пользователь с таким логином уже существует"
                login.value = ""
            } else {
                DBManager.addUser(login = login.value, password = password.value, isAdmin = switchOn.value)
                getUsers()
                login.value = ""
                password.value = ""
                switchOn.value = false
                openAlertDialog.value = false
            }
        }
    }

    fun deleteUser(user: User) {
        scope.launch {
            DBManager.deleteUser(user)
            userList.remove(user)
        }
    }
    fun updateUser(user: User) {
        scope.launch {
            DBManager.updateUser(user, login.value, password.value, switchOn.value)
            getUsers()
            login.value = ""
            password.value = ""
            switchOn.value = false
        }
    }
}