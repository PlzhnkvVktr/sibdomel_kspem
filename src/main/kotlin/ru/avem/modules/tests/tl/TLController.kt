package ru.avem.modules.tests.tl

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.models.SelectedTestObject
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.ATR
import ru.avem.modules.tests.CustomController.appendMessageToLog
import ru.avem.modules.tests.CustomController.checkLatrZero
import ru.avem.modules.tests.CustomController.initARN
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.latrEndsState
import ru.avem.modules.tests.CustomController.pr102
import ru.avem.modules.tests.CustomController.setVoltage
import ru.avem.modules.tests.CustomController.voltage
import ru.avem.modules.tests.Test
import ru.avem.modules.tests.utils.sleepWhileRun
import ru.avem.viewmodels.TestScreenViewModel
import kotlin.math.abs

suspend fun TestScreenViewModel.startMeasurementTL(testItemLine: MutableState<MutableIterator<SelectedTestObject>>) {

    if (isTestRunning.value and testItemLine.value.hasNext()) {


    } else {
        isTestRunning.value = false
    }

}