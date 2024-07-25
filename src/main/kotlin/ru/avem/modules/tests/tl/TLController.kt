package ru.avem.modules.tests.tl

import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.common.af
import ru.avem.db.SteelMarks
import ru.avem.db.SteelMarksObjects
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.ATR
import ru.avem.modules.tests.CustomController.appendMessageToLog
import ru.avem.modules.tests.CustomController.checkLatrZero
import ru.avem.modules.tests.CustomController.indState
import ru.avem.modules.tests.CustomController.initARN
import ru.avem.modules.tests.CustomController.initPM130
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.modules.tests.CustomController.izmState
import ru.avem.modules.tests.CustomController.ktrAmperage
import ru.avem.modules.tests.CustomController.pr102
import ru.avem.modules.tests.CustomController.testObject
import ru.avem.modules.tests.CustomController.voltage
import ru.avem.modules.tests.utils.toDoubleOrDefault
import ru.avem.utils.autoformat
import ru.avem.viewmodels.TestScreenViewModel

var cosB = 0.0
var u_b = 0.0
suspend fun TestScreenViewModel.startMeasurementTL() {


    ktrAmperage = 10.0 / 5.0

    lateinit var ourObjectType: SteelMarksObjects

    var outsideDiametr: Double = 0.0
    var l: Double = 0.0
    var lossesMark: Double = 0.0
    var intensityMark: Double = 0.0
    var densityMark: Double = 0.0
    var error: Double = 0.0
    var h: Double = 0.0
    var lakt: Double = 0.0
    var lsr: Double = 0.0
    var sh: Double = 0.0
    var v: Double = 0.0
    var m: Double = 0.0
    var uSpecified: Double = 0.0
    var iSpecified: Double = 0.0

    val vitkovIzm = 20.0
    val vitkovSil = 21.0

    val listI = mutableListOf<Double>()
    val listP = mutableListOf<Double>()


    if (isTestRunning.value) {
        ourObjectType = transaction {//todo есои стали нет
            SteelMarksObjects.find {
                SteelMarks.name eq testObject.steel_mark
            }.first()
        }
        val p: Double = ourObjectType.density.toDouble()
        lossesMark = ourObjectType.losses.toDouble()
        intensityMark = ourObjectType.intensity.toDouble()
        densityMark = ourObjectType.density.toDouble()
        error = ourObjectType.error.toDouble()

        val ki: Double = if (testObject.isolation) { //TODO proverit
            0.93 // для лака
        } else {
            0.95
        }

        appendMessageToLog("${testObject.isolation} --- ${testObject.material} ")
        outsideDiametr = testObject.d_outer.toDoubleOrDefault(0.0)
        l = testObject.stator_length.toDoubleOrDefault(0.0)
        h = testObject.height_slot.toDoubleOrDefault(0.0)
        lakt = ki * l * 0.001
        lsr = Math.PI * (outsideDiametr - h) * 0.001
        sh = lakt * h * 0.001
        v = lsr * sh
        m = v * p
        appendMessageToLog("m = $m")
        uSpecified = 4.44 * vitkovIzm * 50 * sh
        iSpecified = intensityMark * Math.PI * (outsideDiametr - h) / vitkovIzm / 1000.0

        testItem.u_set_tl.value = uSpecified.af()

        appendMessageToLog("$iSpecified")
        if (uSpecified >= 150) {
            isTestRunning.value = false
            appendMessageToLog("расчетное напряжение ${uSpecified.af()} больше допустимого", LogType.ERROR)
        }

        if (iSpecified > 11) {
            isTestRunning.value = false
            appendMessageToLog("расчетный ток ${iSpecified.af()} больше допустимого", LogType.ERROR)
        }

        if (uSpecified < 50) {
            pr102.tl50V(true)
            delay(500)
        } else if (uSpecified < 150.0) {
            pr102.tl150V(true)
            delay(500)
        } else {
            isTestRunning.value = false
            appendMessageToLog("расчетное напряжение ${uSpecified.af()} больше допустимого", LogType.ERROR)
        }
    }

    if (isTestRunning.value) {
        if (!izmState) {
            isTestRunning.value = false
            appendMessageToLog("Измерительная катушка не подключена", LogType.ERROR)
        }
        if (!indState) {
            isTestRunning.value = false
            appendMessageToLog("Индукторная катушка не подключена", LogType.ERROR)
        }
    }
    if (isTestRunning.value) {
        initPM130(this)
    }
    if (isTestRunning.value) {
        pr102.arn(true)
        delay(500)
    }
    if (isTestRunning.value) {
        initARN()
    }
    if (isTestRunning.value) {
        checkLatrZero()
    }
    if (isTestRunning.value) {
        pr102.u_meas(true)
        appendMessageToLog("U meas", LogType.DEBUG)
        delay(500)
    }
    if (isTestRunning.value) {
        pr102.tl(true)
        delay(500)
    }

    if (isTestRunning.value) {
        val devTimer = System.currentTimeMillis()
        appendMessageToLog("Подъём напряжения", LogType.MESSAGE)
        delay(2000)
        while ((u_a.value.toDouble() < uSpecified - 10) && isTestRunning.value) {
            ATR.startUpLATRPulse(250f, 70f)
            if (CustomController.latrEndsState == 1) {
                appendMessageToLog("Достигнуто максимальное напряжение АРН", LogType.ERROR)
                isTestRunning.value = false
                break
            }
            if (System.currentTimeMillis() - devTimer > 5000 && CustomController.latrEndsState == 2) {
                appendMessageToLog("Застревание АРН", LogType.ERROR)
                isTestRunning.value = false
            }
            if (System.currentTimeMillis() - devTimer > 100000 && voltage < 10) {
                appendMessageToLog(("не появилось напряжение"), LogType.ERROR)
                isTestRunning.value = false
            }
            if (i_u.value.toDouble() > 11) {
                appendMessageToLog("Значение тока превышено", LogType.ERROR)
                isTestRunning.value = false
            }
            delay(100)
        }
        while ((u_a.value.toDouble() < uSpecified - 0.3) && isTestRunning.value) {
            ATR.startUpLATRPulse(250f, 30f)
            delay(100)
            if (CustomController.latrEndsState == 1) {
                appendMessageToLog("Максимальное напряжение АРН", LogType.ERROR)
                isTestRunning.value = false
                break
            }
            if (i_u.value.toDouble() > 11) {
                appendMessageToLog("Значение тока превышено", LogType.ERROR)
                isTestRunning.value = false
            }
        }
        ATR.stopLATR()
        delay(1000)
    }
    try {

        appendMessageToLog("cos $cosB")
        appendMessageToLog("u $u_b ")
        val r = 0.135 // активное сопротивление измерительной катушки

        val p = pA.value.toDouble()
        val i = i_u.value.toDouble()
        val measuringBf = u_a.value.toDouble() / (4.44 * vitkovIzm * 50 * sh)
        val measuringPf = (p - i * i * r) * (1.0 / measuringBf) * (1.0 / measuringBf)
        val measuringPt = if (!testObject.material) {
            measuringPf / m - ((measuringPf / m) / 10.0)  // для стали
        } else {
            measuringPf / m
        }

        val measuringHf = (i * vitkovSil) / lsr

//        protocolModel.operator = position1
//        protocolModel.objectPower = objectModel!!.power
//        protocolModel.objectFreq = objectModel!!.freq
//        protocolModel.objectName = objectModel!!.name
//        protocolModel.objectSerial = serialNum
//        protocolModel.steelName = ourObjectType.name
//        protocolModel.steelDensity = ourObjectType.density
//        protocolModel.steelPower = ourObjectType.losses
//        protocolModel.steelHf = ourObjectType.intensity
//        protocolModel.date = SimpleDateFormat("dd.MM.y").format(System.currentTimeMillis())
//        protocolModel.time = SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())
//        protocolModel.result = if (lossesMark * ((100 + error) / 100.0) >= measuringPt) {
//            recommend
//        } else {
//            notRecommend
//        }
//
//        protocolModel.uB = uIzm
//        protocolModel.iA = i
//        protocolModel.pA = p
//        protocolModel.measuringBf = measuringBf
//        protocolModel.measuringPf = measuringPf
//        protocolModel.measuringPt = measuringPt
//        protocolModel.measuringHf = measuringHf
//
//        runLater {

        val res = measuringPt / ourObjectType.losses.toDouble() - 1.0 < ourObjectType.error.toDouble() / 100.0
        testItem.induction_tl.value = measuringBf.autoformat()
        testItem.p_steel_tl.value = measuringPf.autoformat()
        testItem.losses_tl.value = measuringPt.autoformat()
        testItem.intensity_tl.value = measuringHf.autoformat()
        testItem.res_tl.value = if (res) "Соответствует" else "Не соответствует"
//        }

    } catch (e: Exception) {
        appendMessageToLog("Не удалось рассчитать значения", LogType.ERROR)
    }
}

fun TestScreenViewModel.calculate() {

}