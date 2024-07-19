package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.screenModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.avem.data.enums.TestEnum
import ru.avem.db.DBManager
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.models.SelectedTestObject
import ru.avem.modules.models.TestItem
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.initButtonPost
import ru.avem.modules.tests.CustomController.initPR
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.logMessages
import ru.avem.modules.tests.hh.startMeasurementHH
import ru.avem.modules.tests.ikas.startMeasurementIKAS
import ru.avem.modules.tests.mgr.startMeasurementMGR
import ru.avem.modules.tests.mv.startMeasurementMV
import ru.avem.modules.tests.viu.startMeasurementVIU

class TestScreenViewModel : ScreenModel {

    var isDialog = mutableStateOf(false)
    var isPressStart = mutableStateOf(false)

    var waiting = mutableStateOf(true)

    val loggerScope = CoroutineScope(Dispatchers.Main)

    val currentTest = mutableStateOf<SelectedTestObject?>(null)

    val time: MutableState<String> = mutableStateOf("")
    val result: MutableState<String> = mutableStateOf("")

    val nameTest = TestEnum.nameIKAS.testName
    var warningUV = mutableStateOf(false)
    var warningVW = mutableStateOf(false)
    var warningWU = mutableStateOf(false)

    var u_uv: MutableState<String> = mutableStateOf("")
    var u_vw: MutableState<String> = mutableStateOf("")
    var u_wu: MutableState<String> = mutableStateOf("")
    var i_u: MutableState<String> = mutableStateOf("")
    var i_v: MutableState<String> = mutableStateOf("")
    var i_w: MutableState<String> = mutableStateOf("")

    var testItem = TestItem()

    init {
        screenModelScope.launch {
            CustomController.testObject = DBManager.getTI(CustomController.testObjectName.value)
        }
        logMessages.clear()
    }

    fun start (
        testItemLine: MutableState<MutableIterator<SelectedTestObject>>,
        testName: TestEnum
    ) {
        screenModelScope.launch(
            Dispatchers.Default
        ) {

            logMessages.clear()
            waiting.value = false
            isTestRunning.value = true
            isDialog.value = true
            while (isDialog.value) {
                delay(200)
            }
            if (isTestRunning.value) initPR()
            if (isTestRunning.value) initButtonPost()
            waiting.value = true
            when (testName) {
                TestEnum.nameMGR -> startMeasurementMGR()
                TestEnum.nameVIU -> startMeasurementVIU()
                TestEnum.nameIKAS -> startMeasurementIKAS()
                TestEnum.nameHH -> startMeasurementHH(testItemLine)
                TestEnum.nameMV -> startMeasurementMV(testItemLine)
                TestEnum.nameTL -> startMeasurementMV(testItemLine)
            }
            CustomController.stopTestRunning()
        }
    }




}
