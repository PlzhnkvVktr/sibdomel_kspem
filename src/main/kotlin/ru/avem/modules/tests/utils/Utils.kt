package ru.avem.modules.tests.utils

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay
import ru.avem.modules.devices.CM
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.utils.cleanForParsing
import java.lang.Thread.sleep

fun String?.toDoubleOrDefault(default: Double) = this?.cleanForParsing()?.toDoubleOrNull() ?: default

fun waitingСonfirm (trigger: MutableState<Boolean>) {
    while (trigger.value) {
        sleep(500)
    }
}

fun ms() = System.currentTimeMillis()

suspend fun sleepWhileRun(sec: Double) {
    if (isTestRunning.value) {
        var timer = sec
        while (isTestRunning.value && timer > 0) {
            delay(100)
            timer -= 0.1
        }
    }
}

fun waitWhileRun(sec: Int = 30, isRunning: MutableState<Boolean>) {
    if (isRunning.value) {
        var timer = sec
        while (isRunning.value && timer > 0) {
            sleep(1000)
            timer -= 1
        }
    }
}



fun stopTest(isRunning: MutableState<Boolean>) {
    isRunning.value = false
    CM.clearPollingRegisters()
    CM.clearWritingRegisters()
//    CM.stop()
}