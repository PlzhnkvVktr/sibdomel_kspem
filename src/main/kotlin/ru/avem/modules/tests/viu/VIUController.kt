package ru.avem.modules.tests.viu

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.devices.CM
import ru.avem.modules.devices.avem.avem4.AVEM4Model
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.ATR
import ru.avem.modules.tests.CustomController.appendMessageToLog
import ru.avem.modules.tests.CustomController.checkLatrZero
import ru.avem.modules.tests.CustomController.initARN
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.ktrAmperage
import ru.avem.modules.tests.CustomController.latrEndsState
import ru.avem.modules.tests.CustomController.parma41
import ru.avem.modules.tests.CustomController.pr102
import ru.avem.modules.tests.CustomController.setVoltage
import ru.avem.modules.tests.CustomController.stopTestRunning
import ru.avem.modules.tests.CustomController.testObject
import ru.avem.modules.tests.CustomController.voltOnATR
import ru.avem.modules.tests.CustomController.voltage
import ru.avem.modules.tests.utils.sleepWhileRun
import ru.avem.viewmodels.TestScreenViewModel
import kotlin.math.abs

suspend fun TestScreenViewModel.startMeasurementVIU() {


        ktrAmperage = 1.0 / 5.0
        var setI = testObject.i_viu.toDouble()
        var i_viu = 0.0
        setVoltage = testObject.u_viu.toDouble()
        val setTime = testObject.t_viu.toDouble()

        if (isTestRunning.value) {
            var timer = 300
            while (isDialog.value && timer > 0) {
                delay(100)
                timer -= 1
            }
            if (isTestRunning.value) parma41.checkResponsibility()
            if (parma41.isResponding) {

                CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_A_REGISTER) {
                    i_viu = it.toDouble() * 1 / 5 * 1000.0
                    testItem.i_viu.value = i_viu.af()
                    if (i_viu > setI && isTestRunning.value) {
                        appendMessageToLog("Превышение заданного тока", LogType.ERROR)
                        isTestRunning.value = false
                    }
                }
            } else {
                appendMessageToLog("Парма не отвечает", LogType.ERROR)
                isTestRunning.value = false
            }
        }


        if (isTestRunning.value) {
            appendMessageToLog("Инициализация АВЭМ3...", LogType.MESSAGE)
            CustomController.pv24.checkResponsibility()
            delay(1000)

            if (CustomController.pv24.isResponding) {
                CM.startPoll(CM.DeviceID.PV24.name, AVEM4Model.RMS_VOLTAGE) { value ->
                    voltage = value.toDouble()
                    testItem.u_viu.value = voltage.af()
                }
            } else {
                appendMessageToLog("АВЭМ3 не отвечает", LogType.ERROR)
                isTestRunning.value = false
            }
        }

        if (isDialog.value) isTestRunning.value = false
        if (isTestRunning.value) initARN()
        if (isTestRunning.value) ATR.resetLATR()
        if (isTestRunning.value) pr102.arn(true)
        if (isTestRunning.value) checkLatrZero()
        if (isTestRunning.value) pr102.viu(true)
        if (isTestRunning.value) {
            val devTimer = System.currentTimeMillis()
            appendMessageToLog("Подъём напряжения", LogType.MESSAGE)
            while ((voltage < setVoltage - 200) && isTestRunning.value) {
                ATR.startUpLATRPulse(250f, 50f)
                if (latrEndsState == 1) {
                    appendMessageToLog("Достигнуто максимальное напряжение АРН", LogType.ERROR)
                    isTestRunning.value = false
                    break
                }
                if (voltOnATR > 10) {
                if (System.currentTimeMillis() - devTimer > 4000 && (voltOnATR * 4000.0 / 220.0) !in voltage * 0.6..voltage * 1.6) {
                    appendMessageToLog(
                        "${System.currentTimeMillis() - devTimer > 4000},${setVoltage}, ${CustomController.voltOnATR * 2000.0 / 220.0}, ${voltage * 0.6} - ${voltage * 1.6}",
                        LogType.ERROR
                    )
                    appendMessageToLog(
                        "Несоответствие напряжение расчетного и измеренного напряжения ВИУ",
                        LogType.ERROR
                    )
                    isTestRunning.value = false
                }}
                if (System.currentTimeMillis() - devTimer > 5000 && latrEndsState == 2) {
                    appendMessageToLog("Застревание АРН", LogType.ERROR)
                    isTestRunning.value = false
                }
                if (System.currentTimeMillis() - devTimer > 10000 && voltage < 15) {
                    appendMessageToLog(("no tension appeared"), LogType.ERROR)
                    isTestRunning.value = false
                }
                if (!isTestRunning.value) {
                    testItem.res_viu.value = "Испытание прервано"
                }
                delay(100)
            }
            while ((voltage < setVoltage - 15) && isTestRunning.value) {
                ATR.startUpLATRPulse(250f, 20f)
                delay(100)
                if (latrEndsState == 1) {
                    appendMessageToLog("Максимальное напряжение АРН", LogType.ERROR)
                    isTestRunning.value = false
                    break
                }
                if (voltOnATR > 10) {
                if ((voltOnATR * 4000.0 / 220.0) !in voltage * 0.7..voltage * 1.6) {
                        appendMessageToLog(
                            "Несоответствие напряжение расчетного и измеренного напряжения ВИУ",
                            LogType.ERROR
                        )
                        isTestRunning.value = false
                    }
                }
            }
            ATR.stopLATR()
            if (!isTestRunning.value) {
                testItem.res_viu.value = "Испытание прервано"
            }
        }

        if (isTestRunning.value) {
            delay(1000)
            appendMessageToLog("Выдержка напряжения", LogType.MESSAGE)
            var timer = setTime
            while (isTestRunning.value && timer > 0) {
                delay(100)
                timer -= 0.1
                testItem.t_viu.value = abs(timer).af()
            }
            if (isTestRunning.value) {
                testItem.res_viu.value = "Успешно"
            } else {
                testItem.res_viu.value = "Прервано"
            }
//                addReport(viewModel)


            if (isTestRunning.value) {
            sleepWhileRun(1.0)
            ATR.resetLATR()
            while (voltage > 100) {
                delay(100)
            }
            delay(1000)
        }
    }
}