package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.avem.data.models.ParamOI
import ru.avem.db.DBManager

class EditorOIScreenViewModel : ScreenModel {

    val dialogWindow = mutableStateOf(false)
    val dialogText = mutableStateOf("Готово")
    var typesTI = DBManager.getAllTI().ifEmpty { listOf("") }
    var selectedTI = mutableStateOf(typesTI.first())

    var addNewTI: MutableState<Boolean> = mutableStateOf(false)
    var validateField1 = mutableStateOf(false)
    var validateField2 = mutableStateOf(false)
    var validateField3 = mutableStateOf(false)
    var validateField4 = mutableStateOf(false)
    var validateField5 = mutableStateOf(false)
    var validateField6 = mutableStateOf(false)
    var validateField7 = mutableStateOf(false)
    var validateField8 = mutableStateOf(false)
    var validateField9 = mutableStateOf(false)
    var validateField10 = mutableStateOf(false)
    var validateField11 = mutableStateOf(false)
    var validateField12 = mutableStateOf(false)
    var validateField13 = mutableStateOf(false)
    var validateField14 = mutableStateOf(false)
    var validateField15 = mutableStateOf(false)

    // текущие параметры
    var name = mutableStateOf("")
    var scheme = mutableStateOf(true)
    var power = mutableStateOf("")
    var u_linear = mutableStateOf("")
    var i = mutableStateOf("")
    var i_viu = mutableStateOf("")
    var i_mz = mutableStateOf("")
    var u_viu = mutableStateOf("")
    var u_mgr = mutableStateOf("")
    var t_viu = mutableStateOf("")
    var t_hh = mutableStateOf("")
    var t_mv = mutableStateOf("")
    var r_max = mutableStateOf("")
    var r_min = mutableStateOf("")
    var r20_max = mutableStateOf("")
    var r20_min = mutableStateOf("")
    var t = mutableStateOf("")

    val parapmsList = listOf(
        ParamOI("Мощность, кВт", power, 1, 200, validateField1),
        ParamOI("Напряжение линейное, В", u_linear, 380, 400, validateField2),
        ParamOI("Ток, А", i, 1, 100, validateField3),
        ParamOI("Напряжение ВИУ, В", u_viu, 0, 2000, validateField4),
        ParamOI("Напряжение испытания мегаомметром, В", u_mgr, 500, 2500, validateField5),
        ParamOI("Температурный Kr для расчета, приведённого R, 1/°C", t, -99999999, 999999999, validateField6),
        ParamOI("Сопротивление изоляции максимальное, МОм", r_max, 50, 200000, validateField7),
        ParamOI("Сопротивление изоляции минимальное, МОм", r_min, 50, 200000, validateField8),
        ParamOI("Сопротивление фазы статора при 20°С максимальное, Ом", r20_max, 0, 999999999,  validateField9),
        ParamOI("Сопротивление фазы статора при 20°С минимальное, Ом", r20_min, 0, 99999999, validateField10),
        ParamOI("Время испытания ВИУ, сек", t_viu, 0, 3600, validateField11),
        ParamOI("Время испытания ХХ, мин", t_hh, 0, 999999999, validateField12),
        ParamOI("Время испытания МВЗ, сек", t_mv, 0, 60, validateField13),
        ParamOI("Допустимый ток утечки ВИУ, А", i_viu, 0, 0.24, validateField14),
        ParamOI("Допустимое повышение тока при МВЗ, %", i_mz, 0, 5, validateField15),

//        ParamOI("Внутренний диаметр статора", i_mz, 0, 9999, validateField15),
//        ParamOI("Внешний диаметр статора", i_mz, 0, 5, validateField15),
//        ParamOI("Длинна пакета статора", i_mz, 0, 5, validateField15),
//        ParamOI("Высота спинки паза", i_mz, 0, 5, validateField15),
//        ParamOI("Марка стали", i_mz, 0, 5, validateField15),
//        ParamOI("Тип изоляции пластин", i_mz, 0, 5, validateField15),
//        ParamOI("Плотность материала (стали)", i_mz, 0, 5, validateField15),
//        ParamOI("Удельные потери (для 1 Тл при 50 Гц)", i_mz, 0, 5, validateField15)

    )

    fun openDialogAndUpdate (text: String) {
        screenModelScope.launch {
            dialogText.value = text
            dialogWindow.value = true
            typesTI = DBManager.getAllTI().ifEmpty { listOf("") }
            selectedTI.value = typesTI.first()
            delay(1500)
            dialogWindow.value = false
        }
    }

    fun initTestTI () {
        screenModelScope.launch {
            addNewTI.value = false
            val ti = DBManager.getTI(selectedTI.value)
            name.value = ti.name
            scheme.value = ti.scheme
            power.value = ti.power
            u_linear.value = ti.u_linear
            i.value = ti.i
            i_viu.value = ti.i_viu
            i_mz.value = ti.i_mz
            u_viu.value = ti.u_viu
            u_mgr.value = ti.u_mgr
            t_viu.value = ti.t_viu
            t_hh.value = ti.t_hh
            t_mv.value = ti.t_mv
            r_max.value = ti.r_max
            r_min.value = ti.r_min
            r20_max.value = ti.r20_max
            r20_min.value = ti.r20_min
            t.value = ti.t
        }
    }

    fun clearFields() {
        screenModelScope.launch {
            name.value = ""
            scheme.value = false
            power.value = ""
            u_linear.value = ""
            i.value = ""
            i_viu.value = ""
            i_mz.value = ""
            u_viu.value = ""
            u_mgr.value = ""
            t_viu.value = ""
            t_hh.value = ""
            t_mv.value = ""
            r_max.value = ""
            r_min.value = ""
            r20_max.value = ""
            r20_min.value = ""
            t.value = ""
        }
    }

    fun deleteTI () {
        screenModelScope.launch {
            DBManager.deleteTestItemById(selectedTI.value)
        }
    }

    fun addNewTI () {
        screenModelScope.launch {
            if (!addNewTI.value) {
                DBManager.deleteTestItemById(selectedTI.value)
            }
            DBManager.addTI(
                name.value,
                scheme.value,
                power.value,
                u_linear.value,
                i.value,
                i_viu.value,
                i_mz.value,
                u_viu.value,
                u_mgr.value,
                t_viu.value,
                t_hh.value,
                t_mv.value,
                r_max.value,
                r_min.value,
                r20_max.value,
                r20_min.value,
                t.value
            )
            addNewTI.value = false
        }
    }

}