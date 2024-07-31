package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.avem.data.models.ParamOI
import ru.avem.db.DBManager

class EditorMarkSteelScreenViewModel : ScreenModel {

    val dialogWindow = mutableStateOf(false)
    val dialogText = mutableStateOf("Готово")
    var typesSteelEditScreen = DBManager.getAllSteelMarks().ifEmpty { listOf("") }
    var selectedSteelEditScreen = mutableStateOf(typesSteelEditScreen.first())

    var addNewSteel: MutableState<Boolean> = mutableStateOf(false)

    var nameSteel = mutableStateOf("")
    var densitySteel = mutableStateOf("")
    var lossesSteel = mutableStateOf("")
    var intensitySteel = mutableStateOf("")
    var errorSteel = mutableStateOf("")

    val marksSteelList = listOf(
        ParamOI("Название", nameSteel, 1, 200, isString = true),
        ParamOI("Плотность, кг/м3", densitySteel, 0, 9999),
        ParamOI("Удельные потери (для 1 Тл при 50 Гц), Вт/кг", lossesSteel, 0, 9999),
        ParamOI("Напряженность, А/м", intensitySteel, 0, 9999),
        ParamOI("Допуск годности (больше на), %", errorSteel, 0, 9999),
    )

    fun openDialogAndUpdate (text: String) {
        screenModelScope.launch {
            dialogText.value = text
            dialogWindow.value = true
            typesSteelEditScreen = DBManager.getAllSteelMarks().ifEmpty { listOf("") }
            selectedSteelEditScreen.value = typesSteelEditScreen.first()
            delay(1500)
            dialogWindow.value = false
        }
    }
    fun initSteelMark () {
        screenModelScope.launch {
            addNewSteel.value = false
            val steel = DBManager.getSteelMark(selectedSteelEditScreen.value)
            nameSteel.value = steel.name
            densitySteel.value = steel.density
            lossesSteel.value = steel.losses
            intensitySteel.value = steel.intensity
            errorSteel.value = steel.error
        }
    }

    fun clearFields() {
        screenModelScope.launch {
            nameSteel.value = ""
            densitySteel.value = ""
            lossesSteel.value = ""
            intensitySteel.value = ""
            errorSteel.value = ""
        }
    }

    fun deleteSteelMark () {
        screenModelScope.launch {
            DBManager.deleteSteelMark(selectedSteelEditScreen.value)
        }
    }

    fun addNewSteelMark() {
        screenModelScope.launch {
            if (!addNewSteel.value) {
                DBManager.deleteSteelMark(selectedSteelEditScreen.value)
            }
            DBManager.addSteelMark(
                nameSteel.value,
                densitySteel.value,
                lossesSteel.value,
                intensitySteel.value,
                errorSteel.value,
            )
        }
        addNewSteel.value = false
    }
}
