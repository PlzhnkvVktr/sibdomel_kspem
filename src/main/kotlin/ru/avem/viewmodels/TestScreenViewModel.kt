package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.avem.common.ProtocolBuilder
import ru.avem.data.enums.TestEnum
import ru.avem.db.DBManager
import ru.avem.modules.models.SelectedTestObject
import ru.avem.modules.models.TestItem
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.initButtonPost
import ru.avem.modules.tests.CustomController.initPR
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.logMessages
import ru.avem.modules.tests.hh.startMeasurementHHandMVZ
import ru.avem.modules.tests.ikas.startMeasurementIKAS
import ru.avem.modules.tests.mgr.startMeasurementMGR
import ru.avem.modules.tests.tl.startMeasurementTL
import ru.avem.modules.tests.viu.startMeasurementVIU
import ru.avem.utils.getCurrentDate
import ru.avem.utils.getCurrentTime

class TestScreenViewModel : ScreenModel {

    var isDialog = mutableStateOf(false)
    var isPressStart = mutableStateOf(false)

    var waiting = mutableStateOf(true)

    val loggerScope = CoroutineScope(Dispatchers.Main)

    val time: MutableState<String> = mutableStateOf("")
    val result: MutableState<String> = mutableStateOf("")

    var warningUV = mutableStateOf(false)
    var warningVW = mutableStateOf(false)
    var warningWU = mutableStateOf(false)

    var u_a: MutableState<String> = mutableStateOf("")
    var u_uv: MutableState<String> = mutableStateOf("")
    var u_vw: MutableState<String> = mutableStateOf("")
    var u_wu: MutableState<String> = mutableStateOf("")
    var i_u: MutableState<String> = mutableStateOf("")
    var i_v: MutableState<String> = mutableStateOf("")
    var i_w: MutableState<String> = mutableStateOf("")
    var cos: MutableState<String> = mutableStateOf("")
    var pA: MutableState<String> = mutableStateOf("")
    var uSet: MutableState<String> = mutableStateOf("")

    var testItem = TestItem()

    init {
        screenModelScope.launch {
            CustomController.testObject = DBManager.getTI(CustomController.testObjectName.value)
        }
        logMessages.clear()
    }

    fun start (
        testName: TestEnum
    ) {
        clearFields()
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
                TestEnum.nameMGR -> {
                    startMeasurementMGR()
                    addReportMGR()
                }
                TestEnum.nameVIU -> {
                    startMeasurementVIU()
                }
                TestEnum.nameIKAS -> {
                    startMeasurementIKAS()
                    addReportIKAS()
                }
                TestEnum.nameHHandMVZ -> {
                    startMeasurementHHandMVZ()
                    addReportHHandMVZ()
                }
                TestEnum.nameTL -> {
                    startMeasurementTL()
                    addReportTL()
                }
            }
            CustomController.stopTestRunning()
        }
    }

    fun addReportMGR() {
        with(ProtocolBuilder) {
            u = testItem.mgrU.value
            r15 = testItem.r15.value
            r60 = testItem.r60.value
            kABS = testItem.kABS.value
            operator = CustomController.currentOperator.value
            date = getCurrentDate()
            time = getCurrentTime()
            mgrResult = testItem.res_mgr.value
        }
    }

    fun addReportIKAS() {
        with(ProtocolBuilder) {
            r_uv_ikas = testItem.r_uv_ikas.value
            r_vw_ikas = testItem.r_vw_ikas.value
            r_wu_ikas = testItem.r_wu_ikas.value
            calc_u_ikas = testItem.calc_u_ikas.value
            calc_v_ikas = testItem.calc_v_ikas.value
            calc_w_ikas = testItem.calc_w_ikas.value
            deviation = testItem.deviation.value
            ikasResult = testItem.res_ikas.value
            operator = CustomController.currentOperator.value
            date = getCurrentDate()
            time = getCurrentTime()
        }
    }
    fun addReportVIU() {
        with(ProtocolBuilder) {
            u_viu = testItem.u_viu.value
            i_viu = testItem.i_viu.value
            t_viu = testItem.t_viu.value
            viuResult = testItem.res_viu.value
            operator = CustomController.currentOperator.value
            date = getCurrentDate()
            time = getCurrentTime()
        }
    }

    fun addReportHHandMVZ() {
        with(ProtocolBuilder) {
            u_uv_hh = testItem.u_uv_hh.value
            u_vw_hh = testItem.u_vw_hh.value
            u_wu_hh = testItem.u_wu_hh.value
            i_u_hh = testItem.i_u_hh.value
            i_v_hh = testItem.i_v_hh.value
            i_w_hh = testItem.i_w_hh.value
            cos_hh = testItem.cos_hh.value
            deviation = testItem.i_deviation_hh.value
            hhResult = testItem.hhResult.value
            operator = CustomController.currentOperator.value
            date = getCurrentDate()
            time = getCurrentTime()
        }
    }

    fun addReportTL() {
        with(ProtocolBuilder) {
            u_set_tl = testItem.u_set_tl.value
            u_izm_tl = testItem.u_izm_tl.value
            i_u_tl = testItem.i_u_tl.value
            p_tl = testItem.p_tl.value
            induction_tl = testItem.induction_tl.value
            p_steel_tl = testItem.p_steel_tl.value
            intensity_tl = testItem.intensity_tl.value
            losses_tl = testItem.losses_tl.value
            tlResult = testItem.res_tl.value
            operator = CustomController.currentOperator.value
            date = getCurrentDate()
            time = getCurrentTime()
        }
    }
    fun clearFields() {
        screenModelScope.launch(
            Dispatchers.Default
        ) {
            with(testItem) {
                specifiedMgrU.value = ""
                mgrU.value = ""
                r15.value = ""
                r60.value = ""
                kABS.value = ""
                timeMGR.value = ""
                specifiedU_viu.value = ""
                specifiedI_viu.value = ""
                specifiedT_viu.value = ""
                u_viu.value = ""
                i_viu.value = ""
                t_viu.value = ""
                res_viu.value = ""
                ikas_i.value = ""
                ikas_v.value = ""
                r_uv_ikas.value = ""
                r_vw_ikas.value = ""
                r_wu_ikas.value = ""
                calc_u_ikas.value = ""
                calc_v_ikas.value = ""
                calc_w_ikas.value = ""
                deviation.value = ""
                u_uv_hh.value = ""
                u_vw_hh.value = ""
                u_wu_hh.value = ""
                i_u_hh.value = ""
                i_v_hh.value = ""
                i_w_hh.value = ""
                cos_hh.value = ""
                hhResult.value = ""
                i_deviation_hh.value = ""
                u_set_tl.value = ""
                u_izm_tl.value = ""
                i_u_tl.value = ""
                p_tl.value = ""
                induction_tl.value = ""
                p_steel_tl.value = ""
                intensity_tl.value = ""
                losses_tl.value = ""
                res_tl.value = ""
            }
        }
    }




}
