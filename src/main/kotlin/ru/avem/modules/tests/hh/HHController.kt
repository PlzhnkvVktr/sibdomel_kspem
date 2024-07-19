package ru.avem.modules.tests.hh

import androidx.compose.runtime.MutableState
import ru.avem.modules.models.SelectedTestObject
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.initPM130
import ru.avem.modules.tests.Test
import ru.avem.viewmodels.TestScreenViewModel

suspend fun TestScreenViewModel.startMeasurementHH(testItemLine: MutableState<MutableIterator<SelectedTestObject>>) {

    if (CustomController.isTestRunning.value and testItemLine.value.hasNext()) {


        initPM130(this)
    } else {
        CustomController.isTestRunning.value = false
    }

}



