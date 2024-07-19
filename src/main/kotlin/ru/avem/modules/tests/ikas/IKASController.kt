package ru.avem.modules.tests.ikas

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay
import ru.avem.common.ProtocolBuilder
import ru.avem.common.af
import ru.avem.db.TestItem
import ru.avem.modules.models.SelectedTestObject
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.devices.avem.avem4.AVEM4Model
import ru.avem.modules.devices.avem.avem7.AVEM7Model
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.appendMessageToLog
import ru.avem.modules.tests.CustomController.initAVEM4
import ru.avem.modules.tests.CustomController.initAVEM7
import ru.avem.modules.tests.CustomController.isStartButton
import ru.avem.modules.tests.CustomController.isStartPressed
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.pa62
import ru.avem.modules.tests.CustomController.pr102
import ru.avem.modules.tests.CustomController.pv61
import ru.avem.modules.tests.Test
import ru.avem.modules.tests.utils.waitingСonfirm
import ru.avem.viewmodels.TestScreenViewModel
import java.util.*
import kotlin.math.abs

suspend fun TestScreenViewModel.startMeasurementIKAS() {
    if (isTestRunning.value) {
        var resistanceAB = 0.0
        var resistanceBC = 0.0
        var resistanceCA = 0.0
        initAVEM4(this)
        initAVEM7(this)
        if (isTestRunning.value) {
            resistanceAB = meas(warningUV, 1)
            if (resistanceAB.isNaN()) {
                testItem.r_uv_ikas.value = "Обрыв"
            } else {
                testItem.r_uv_ikas.value = String.format(Locale.US, "%.3f", resistanceAB)
            }
        }
        if (isTestRunning.value) {
            resistanceBC = meas(warningVW, 2)
            if (resistanceBC.isNaN()) {
                testItem.r_vw_ikas.value = "Обрыв"
            } else {
                testItem.r_vw_ikas.value = String.format(Locale.US, "%.3f", resistanceBC)
            }
        }

        if (isTestRunning.value) {
            resistanceCA = meas(warningWU, 3)
            if (resistanceCA.isNaN()) {
                testItem.r_wu_ikas.value = "Обрыв"
            } else {
                testItem.r_wu_ikas.value = String.format(Locale.US, "%.3f", resistanceCA)
            }
        }

    } else {
        isTestRunning.value = false
    }
}

//private fun TestScreenViewModel.calcRs(
//    resistanceAB: Double,
//    resistanceBC: Double,
//    resistanceCA: Double,
//    ti: TestItem
//) {
//
//    val tempRatio = 0.00425
//
//    if (testItem.r_uv_ikas.value == "Обрыв" ||
//        testItem.r_vw_ikas.value == "Обрыв" ||
//        testItem.r_wu_ikas.value == "Обрыв"
//    ) {
//        testItem.calc_uv_ikas.value = "Обрыв"
//        testItem.calc_vw_ikas.value = "Обрыв"
//        testItem.calc_wu_ikas.value = "Обрыв"
//
//    } else {
//        if (!ti.scheme) {//TODO указать схему звезда
//            testItem.calc_uv_ikas.value = "%.3f".format(Locale.ENGLISH, ((resistanceCA + resistanceAB - resistanceBC) / 2.0))
//            testItem.calc_vw_ikas.value = "%.3f".format(Locale.ENGLISH, ((resistanceAB + resistanceBC - resistanceCA) / 2.0))
//            testItem.calc_wu_ikas.value = "%.3f".format(Locale.ENGLISH, ((resistanceBC + resistanceCA - resistanceAB) / 2.0))
//        } else {
//            testItem.calc_uv_ikas.value =
//                "%.3f".format(
//                    Locale.ENGLISH,
//                    (2.0 * resistanceAB * resistanceBC / (resistanceAB + resistanceBC - resistanceCA) - (resistanceAB + resistanceBC - resistanceCA) / 2.0)
//                )
//            testItem.calc_vw_ikas.value =
//                "%.3f".format(
//                    Locale.ENGLISH,
//                    (2.0 * resistanceBC * resistanceCA / (resistanceBC + resistanceCA - resistanceAB) - (resistanceBC + resistanceCA - resistanceAB) / 2.0)
//                )
//            testItem.calc_wu_ikas.value =
//                "%.3f".format(
//                    Locale.ENGLISH,
//                    (2.0 * resistanceCA * resistanceAB / (resistanceCA + resistanceAB - resistanceBC) - (resistanceCA + resistanceAB - resistanceBC) / 2.0)
//                )
//        }
//        testItem.deviation.value =
//            ((maxOf(testItem.calc_uv_ikas.value, testItem.calc_vw_ikas.value, testItem.calc_wu_ikas.value).toDouble() - minOf(testItem.calc_uv_ikas.value, testItem.calc_vw_ikas.value, testItem.calc_wu_ikas.value).toDouble())
//                    / maxOf(testItem.calc_uv_ikas.value, testItem.calc_vw_ikas.value, testItem.calc_wu_ikas.value).toDouble() * 100).af()
//
//        val rA = abs(testItem.calc_uv_ikas.value.toDouble())
//        val rB = abs(testItem.calc_vw_ikas.value.toDouble())
//        val rC = abs(testItem.calc_wu_ikas.value.toDouble())
//
//        val t = viewModel.tempAmb.value.toDoubleOrDefault(0.0)
//        val rtK = tempRatio // при 20°C
//        val rtT = 20.0
//
//        viewModel.calcUV.value = "%.3f".format(Locale.ENGLISH, abs(rA / (1 + rtK * (t - rtT))))
//        viewModel.calcVW.value = "%.3f".format(Locale.ENGLISH, abs(rB / (1 + rtK * (t - rtT))))
//        viewModel.calcWU.value = "%.3f".format(Locale.ENGLISH, abs(rC / (1 + rtK * (t - rtT))))
//
//
//
//    }
//}

//fun start(viewModel: IKASViewModel, testItemLine: MutableState<MutableIterator<SelectedTestObject>>) {
//    viewModel.clearFields()
//
//    isTestRunning.value = true
//    var resistanceAB = 0.0
//    var resistanceBC = 0.0
//    var resistanceCA = 0.0
//
//    thread {
//        viewModel.warningUV.value = false
//        viewModel.warningVW.value = false
//        viewModel.warningWU.value = false
//        if (isTestRunning.value) {
//            appendMessageToLog(testItemLine.value.hasNext().toString(), LogType.MESSAGE)
////            CustomController.initPR()
//        }
//
//
//    }
//}

//private fun calcRs(
//    viewModel: IKASViewModel,
//    resistanceAB: Double,
//    resistanceBC: Double,
//    resistanceCA: Double,
//    ti: TestItem
//) {
//
//    val tempRatio = 0.00425
//
//    if (viewModel.Rvw1.value == "Обрыв" ||
//        viewModel.Rvw1.value == "Обрыв" ||
//        viewModel.Rvw1.value == "Обрыв"
//    ) {
//        viewModel.calcUV.value = "Обрыв"
//        viewModel.calcVW.value = "Обрыв"
//        viewModel.calcWU.value = "Обрыв"
//
//    } else {
//        if (!ti.scheme) {//TODO указать схему звезда
//            viewModel.calcUV.value = "%.3f".format(Locale.ENGLISH, ((resistanceCA + resistanceAB - resistanceBC) / 2.0))
//            viewModel.calcVW.value = "%.3f".format(Locale.ENGLISH, ((resistanceAB + resistanceBC - resistanceCA) / 2.0))
//            viewModel.calcWU.value = "%.3f".format(Locale.ENGLISH, ((resistanceBC + resistanceCA - resistanceAB) / 2.0))
//        } else {
//            viewModel.calcUV.value =
//                "%.3f".format(
//                    Locale.ENGLISH,
//                    (2.0 * resistanceAB * resistanceBC / (resistanceAB + resistanceBC - resistanceCA) - (resistanceAB + resistanceBC - resistanceCA) / 2.0)
//                )
//            viewModel.calcVW.value =
//                "%.3f".format(
//                    Locale.ENGLISH,
//                    (2.0 * resistanceBC * resistanceCA / (resistanceBC + resistanceCA - resistanceAB) - (resistanceBC + resistanceCA - resistanceAB) / 2.0)
//                )
//            viewModel.calcWU.value =
//                "%.3f".format(
//                    Locale.ENGLISH,
//                    (2.0 * resistanceCA * resistanceAB / (resistanceCA + resistanceAB - resistanceBC) - (resistanceCA + resistanceAB - resistanceBC) / 2.0)
//                )
//        }
//        viewModel.deviation.value =
//            ((maxOf(viewModel.calcUV.value, viewModel.calcVW.value, viewModel.calcWU.value).toDouble() - minOf(viewModel.calcUV.value, viewModel.calcVW.value, viewModel.calcWU.value).toDouble())
//                    / maxOf(viewModel.calcUV.value, viewModel.calcVW.value, viewModel.calcWU.value).toDouble() * 100).af()
//
//        val rA = abs(viewModel.calcUV.value.toDouble())
//        val rB = abs(viewModel.calcVW.value.toDouble())
//        val rC = abs(viewModel.calcWU.value.toDouble())
//
//        val t = viewModel.tempAmb.value.toDoubleOrDefault(0.0)
//        val rtK = tempRatio // при 20°C
//        val rtT = 20.0
//
//        viewModel.calcUV.value = "%.3f".format(Locale.ENGLISH, abs(rA / (1 + rtK * (t - rtT))))
//        viewModel.calcVW.value = "%.3f".format(Locale.ENGLISH, abs(rB / (1 + rtK * (t - rtT))))
//        viewModel.calcWU.value = "%.3f".format(Locale.ENGLISH, abs(rC / (1 + rtK * (t - rtT))))
//
//
//
//    }
//}

private suspend fun meas(trigger: MutableState<Boolean>, order: Int): Double {
    pr102.ikasBa(false)
    pr102.ikasBc(false)
    pr102.ikasA(false)
    pr102.ikasC (false)
    var resistance: Double = Double.NaN
    trigger.value = true
    delay(100)
    waitingСonfirm(trigger)
    isStartButton.value = true
    while (!isStartPressed.value && isTestRunning.value) {
        delay(100)
    }
    isStartButton.value = false

    if (isTestRunning.value) {
        when (order) {
            1 -> pr102.ikasBa(true)
            2 -> pr102.ikasBc(true)
            3 -> {
                pr102.ikasA(true)
                pr102.ikasC (true)
            }
        }
        delay(5000)
        val voltage = abs(pv61.getRegisterById(AVEM4Model.RMS_VOLTAGE).value.toDouble())
        val current = abs(pa62.getRegisterById(AVEM7Model.AMPERAGE).value.toDouble())
        resistance = voltage / current

        if (0.001 > current) {
            resistance = Double.NaN
        }
    }

    if (isTestRunning.value) {
        pr102.ikasBa(false)
        pr102.ikasBc(false)
        pr102.ikasA(false)
        pr102.ikasC (false)
        delay(500)
    }
    return resistance
}


//fun addReport(ikasVM: IKASViewModel) {
//    with(ProtocolBuilder) {
//
//        Ruv1 = ikasVM.Ruv1.value
//        Rvw1 = ikasVM.Rvw1.value
//        Rwu1 = ikasVM.Rwu1.value
//        Ruv2 = ikasVM.calcUV.value
//        Rvw2 = ikasVM.calcVW.value
//        Rwu2 = ikasVM.calcWU.value
//        deviationIkas = ikasVM.deviation.value
//        operator = CustomController.currentOperator.value
//        date = getCurrentDate()
//        time = getCurrentTime()
//        ikasResult = ikasVM.result.value
//
//    }
//}
