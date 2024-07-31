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
        var u_uv_res = a_u.toString()
        var u_vw_res = b_u.toString()
        var u_wu_res = c_u.toString()
        var i_u_res = a_i.toString()
        var i_v_res = b_i.toString()
        var i_w_res = c_i.toString()
        var cos_res = cos.value

        testItem.u_uv_hh.value = a_u.toString()
        testItem.u_vw_hh.value = b_u.toString()
        testItem.u_wu_hh.value = c_u.toString()
        testItem.i_u_hh.value = a_i.toString()
        testItem.i_v_hh.value = b_i.toString()
        testItem.i_w_hh.value = c_i.toString()
        testItem.cos_hh.value = cos.value

        if (isTestRunning.value) {
            pr102.i_max5(false)
            delay(500)
        }
        if (isTestRunning.value) {
            pr102.km1(false)
            delay(500)
        }

        testItem.i_deviation_hh.value = ((maxOf(a_i, b_i, c_i) - minOf(a_i, b_i, c_i)) / maxOf(a_i, b_i, c_i) * 100).af()
        if (testItem.i_deviation_hh.value == "NaN") testItem.i_deviation_hh.value = "0.0"
        testItem.hhResult.value = if (testItem.i_deviation_hh.value.toDouble() > CustomController.testObject.i_mz.toDouble()) {
            "Отклонение > ${CustomController.testObject.i_mz}"
        } else {
            "Успешно"
        }
        u_uv.value = u_uv_res
        u_vw.value = u_vw_res
        u_wu.value = u_wu_res
        i_u.value = i_u_res
        i_v.value = i_v_res
        i_w.value = i_w_res
        cos.value = cos_res

    } else {
        isTestRunning.value = false
        appendMessageToLog("Испытание прервано", LogType.ERROR)
    }

}



