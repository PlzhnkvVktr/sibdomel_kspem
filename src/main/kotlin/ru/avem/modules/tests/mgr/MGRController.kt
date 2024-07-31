package ru.avem.modules.tests.mgr

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.devices.CM
import ru.avem.modules.devices.avem.avem9.AVEM9Model
import ru.avem.modules.tests.CustomController.appendMessageToLog
import ru.avem.modules.tests.CustomController.initAVEM9
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.pr102
import ru.avem.modules.tests.CustomController.pr66
import ru.avem.modules.tests.CustomController.statusMGR
import ru.avem.modules.tests.CustomController.stopTestRunning
import ru.avem.modules.tests.CustomController.testObject
import ru.avem.viewmodels.TestScreenViewModel

suspend fun TestScreenViewModel.startMeasurementMGR() {
    if (isTestRunning.value) pr102.toggleSwitchMgr()
    if (isTestRunning.value) initAVEM9()
    if (isTestRunning.value) {
        testItem.specifiedMgrU.value = testObject.u_mgr

        var timer = 300
        while (isDialog.value && timer > 0) {
            delay(100)
            timer -= 1
        }
        if (isDialog.value) isTestRunning.value = false
        if (isTestRunning.value) {
            CM.startPoll(CM.DeviceID.PR66.name, pr66.model.STATUS) { value ->
                statusMGR = value.toInt()
            }
            CM.startPoll(CM.DeviceID.PR66.name, pr66.model.VOLTAGE) { value ->
                testItem.mgrU.value = value.af()
            }
            CM.startPoll(CM.DeviceID.PR66.name, pr66.model.R15_MEAS) { value ->
                testItem.r15.value = value.af()
            }
            CM.startPoll(CM.DeviceID.PR66.name, pr66.model.R60_MEAS) { value ->
                testItem.r60.value = value.af()
            }
            CM.startPoll(CM.DeviceID.PR66.name, pr66.model.ABSORPTION) { value ->
                testItem.kABS.value = value.af()
            }
        }
        if (isTestRunning.value) {
            pr102.mgr(true)
            delay(1000)
        }
        if (isTestRunning.value) {
            pr66.startMeasurement(
                AVEM9Model.MeasurementMode.AbsRatio, when {
                    testObject.u_mgr.toInt() == 2500 -> {
                        AVEM9Model.SpecifiedVoltage.V2500
                    }

                    testObject.u_mgr.toInt() == 1000 -> {
                        AVEM9Model.SpecifiedVoltage.V1000
                    }

                    testObject.u_mgr.toInt() == 500 -> {
                        AVEM9Model.SpecifiedVoltage.V500
                    }

                    else -> {
                        AVEM9Model.SpecifiedVoltage.V500
                    }
                }
            )
            appendMessageToLog("Идет измерение...", LogType.ERROR)
        }
        if (isTestRunning.value) delay(3000)
        var time = 70
        while (isTestRunning.value && statusMGR != 4 && time-- > 0) {
            delay(1000)
        }
        appendMessageToLog("Измерение завершено успешно", LogType.MESSAGE)
        testItem.res_mgr.value = "Измерение завершено"
    } else {
        isTestRunning.value = false
        appendMessageToLog("Измерение прервано", LogType.ERROR)
        testItem.res_mgr.value = "Измерение прервано"
    }
    pr66.stopTest()
}