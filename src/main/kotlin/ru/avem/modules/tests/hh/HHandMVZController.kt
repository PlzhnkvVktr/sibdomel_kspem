package ru.avem.modules.tests.hh

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.appendMessageToLog
import ru.avem.modules.tests.CustomController.initPM130
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.pr102
import ru.avem.viewmodels.TestScreenViewModel

suspend fun TestScreenViewModel.startMeasurementHHandMVZ() {
    var a_u = 0.0
    var b_u = 0.0
    var c_u = 0.0
    var a_i = 0.0
    var b_i = 0.0
    var c_i = 0.0

    if (isTestRunning.value) {
        initPM130(this)
        if (isTestRunning.value) {
            pr102.km1(true)
            delay(500)
        }
        if (isTestRunning.value) {
            pr102.i_max5(true)
            delay(500)
        }
        delay(5000)
        a_u = u_uv.value.toDouble()
        b_u = u_vw.value.toDouble()
        c_u = u_wu.value.toDouble()
        a_i = i_u.value.toDouble()
        b_i = i_v.value.toDouble()
        c_i = i_w.value.toDouble()
        if (isTestRunning.value) {
            pr102.i_max5(false)
            delay(500)
        }
        if (isTestRunning.value) {
            pr102.km1(false)
            delay(500)
        }
        u_uv.value = a_u.toString()
        u_vw.value = b_u.toString()
        u_wu.value = c_u.toString()
        i_u.value = a_i.toString()
        i_v.value = b_i.toString()
        i_w.value = c_i.toString()

        testItem.i_deviation_hh.value = ((maxOf(a_i, b_i, c_i) - minOf(a_i, b_i, c_i)) / maxOf(a_i, b_i, c_i) * 100).af()
        if (testItem.i_deviation_hh.value == "NaN") testItem.i_deviation_hh.value = "0.0"
        testItem.hh_result.value = if (testItem.i_deviation_hh.value.toDouble() > CustomController.testObject.i_mz.toDouble()) {
            "Отклонение > ${CustomController.testObject.i_mz}"
        } else {
            "Успешно"
        }

    } else {
        isTestRunning.value = false
        appendMessageToLog("Испытание прервано", LogType.ERROR)
    }

}



