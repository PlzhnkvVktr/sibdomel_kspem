package ru.avem.modules.tests

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.avem.common.af
import ru.avem.common.repos.AppConfig
import ru.avem.db.TestItem
import ru.avem.kspem.devices.parma.ParmaController
import ru.avem.kspem.devices.parma.ParmaModel
import ru.avem.modules.common.logger.LogMessage
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.devices.CM
import ru.avem.modules.devices.avem.atr.ATR
import ru.avem.modules.devices.avem.atr.ATRModel
import ru.avem.modules.devices.avem.avem4.AVEM4
import ru.avem.modules.devices.avem.avem4.AVEM4Model
import ru.avem.modules.devices.avem.avem7.AVEM7
import ru.avem.modules.devices.avem.avem7.AVEM7Model
import ru.avem.modules.devices.owen.pr.PR
import ru.avem.modules.tests.utils.ms
import ru.avem.modules.tests.utils.toDoubleOrDefault
import ru.avem.modules.devices.avem.avem9.AVEM9
import ru.avem.viewmodels.TestScreenViewModel
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import kotlin.experimental.and

object CustomController {
    val pr102 = CM.getDeviceByID<PR>(CM.DeviceID.DD2_1)
    val parma41 = CM.getDeviceByID<ParmaController>(CM.DeviceID.PAV41)
    val ATR = CM.getDeviceByID<ATR>(CM.DeviceID.GV240)
    val pv24 = CM.getDeviceByID<AVEM4>(CM.DeviceID.PV24)
    val pa62 = CM.getDeviceByID<AVEM7>(CM.DeviceID.PA62)
    val pr66 = CM.getDeviceByID<AVEM9>(CM.DeviceID.PR66)
    val pv61 = CM.getDeviceByID<AVEM4>(CM.DeviceID.PV61)

    @Volatile
    var isTestRunning = mutableStateOf(false)

    val currentOperator = mutableStateOf(AppConfig.params.login)

    var isStartPressed = mutableStateOf(false)
    var isStopPressed = mutableStateOf(false)
    var doorZone = mutableStateOf(false)
    var doorSCO = mutableStateOf(false)
    var ikzTI = mutableStateOf(false)
    var ikzVIU = mutableStateOf(false)

    var statusMGR: Int = 0

    var voltOnATR = 1.0
    var ktrVoltage = 1.0
    var voltAverage = 0.0
    var ktrAmperage = 1.0

    var voltage = 0.0
    var setVoltage = 0.0
    var latrEndsState = 0

    val logMessages = mutableStateListOf<LogMessage>()
    var isStartButton = mutableStateOf(false)

    var testObjectName = mutableStateOf("")
    lateinit var testObject: TestItem

    fun appendMessageToLog(text: String, type: LogType = LogType.DEBUG) {
        val msg = "${SimpleDateFormat("HH:mm:ss").format(ms())} | $text"
        logMessages.add(LogMessage(msg, type))
    }

    fun initWatchDogDD2() {
        with(pr102) {
            initWithoutProtections()
            offAllKMs()
            CM.addWritingRegister(name, pr102.model.CMD, 1.toShort())
            CM.startPoll(name, pr102.model.STATE) { }
        }
    }

    suspend fun initButtonPost() {

        appendMessageToLog("Нажмите Пуск", LogType.ERROR)
        isStartButton.value = true
        while (isTestRunning.value && pr102.isResponding) {
            if (isStartPressed.value) {
                isStartButton.value = false
                break
            }
            if (isStopPressed.value) {
                isStartButton.value = false
                isTestRunning.value = false
                appendMessageToLog("Испытание остановлено", LogType.ERROR)
            }
            delay(100)
        }
    }

    suspend fun checkLatrZero() {
        var latrTimer = 300
        while (voltOnATR > 10 && isTestRunning.value) {
            delay(100)
            latrTimer--
            if (latrTimer<0) {
                appendMessageToLog("Напряжение АРН > 10 В", LogType.ERROR)
                isTestRunning.value = false
            }
        }
    }

    fun stopTestRunning() {
        isTestRunning.value = false
        CM.clearPollingRegisters()
        isStartButton.value = false
        pr102.offAllKMs()
        CM.clearWritingRegisters()
        voltOnATR = 1.0
        ktrVoltage = 1.0
        voltAverage = 0.0
        ktrAmperage = 1.0
    }

    suspend fun initPR() {
        pr102.checkResponsibility()
        val scope = CoroutineScope(Dispatchers.Default)
        appendMessageToLog("Инициализация БСУ...", LogType.MESSAGE)
        isStartPressed.value = false
        isStopPressed.value = false
        var stateLock = false
        var count = 0
        if (!pr102.isResponding) {
            appendMessageToLog("ПР102 не отвечает", LogType.ERROR)
            isTestRunning.value = false
        } else {
            pr102.offAllKMs()
            initWatchDogDD2()
            delay(1000)
            CM.startPoll(CM.DeviceID.DD2_1.name, pr102.model.DI_01_16_RAW) { value ->
                isStartPressed.value = value.toShort() and 1 < 1  // 1  // TODO инвертировать обратно
                isStopPressed.value = value.toShort() and 2 > 0   // 2
                doorZone.value = value.toShort() and 4 > 0   // 3
//                             = value.toShort() and 8 > 0   // 4
                doorSCO.value = value.toShort() and 16 > 0  // 5
                ikzTI.value = value.toShort() and 32 < 1 || stateLock // 6
                ikzVIU.value = value.toShort() and 64 < 1  // 7
//                             = value.toShort() and 128 > 0 // 8
            }
            delay(1000)
            scope.launch {
                while (isTestRunning.value) {
                    delay(100)
                    if (!pr102.isResponding) {
                        appendMessageToLog("ПР102 не отвечает", LogType.ERROR)
                        isTestRunning.value = false
                    }
                    if (isStopPressed.value) {
                        appendMessageToLog(("the <STOP> button is pressed"), LogType.ERROR)
                        isTestRunning.value = false
                    }
                    if (doorZone.value) {
                        appendMessageToLog(("zone doors open"), LogType.ERROR)
                        isTestRunning.value = false
                    }
                    if (doorSCO.value) {
                        appendMessageToLog(("the doors are open"), LogType.ERROR)
                        isTestRunning.value = false
                    }
                    if (ikzTI.value) {
                        count++
                        if (count >= 8) {
                            appendMessageToLog(("Overcurrent"), LogType.ERROR)
                            stateLock = true
                            isTestRunning.value = false
                        }
                    } else {
                        count = 0
                    }
                    if (ikzVIU.value) {
                        appendMessageToLog(("Overcurrent"), LogType.ERROR)
                        isTestRunning.value = false
                    }
                    if (!isTestRunning.value) {
                        appendMessageToLog(("Test finished"), LogType.ERROR)
                    }
                }
                stopTestRunning()
            }
        }
    }

    fun initAVEM4 (vm: TestScreenViewModel) {
        with(pv61) {
            checkResponsibility()
            if (!isResponding) {
                appendMessageToLog("АВЭМ-4 не отвечает", LogType.ERROR)
                isTestRunning.value = false
//                    appendMessageToLog("PV21 не отвечает", LogType.ERROR)
            } else {
                CM.startPoll(CM.DeviceID.PV61.name, AVEM4Model.RMS_VOLTAGE) { value ->
                    vm.testItem.ikas_v.value = (value.toDouble()).af()
                }
            }
        }
    }
    fun initAVEM7 (vm: TestScreenViewModel) {
        with(pa62) {
            checkResponsibility()
            if (!isResponding) {
                appendMessageToLog("АВЕМ-7 не отвечает", LogType.ERROR)
                isTestRunning.value = false
            } else {
                CM.startPoll(CM.DeviceID.PA62.name, AVEM7Model.AMPERAGE) { value ->
                    vm.testItem.ikas_v.value = (value.toDouble()).af()
                    if (!pa62.isResponding) {
                        appendMessageToLog("PV21 не отвечает", LogType.ERROR)
                    }
                }
            }
        }
    }
    suspend fun initAVEM9 () {
        appendMessageToLog("Инициализация АВЭМ-9", LogType.MESSAGE)
        var timer = 10
        while (!pr66.isResponding) {
            --timer
            delay(1000)
            pr66.checkResponsibility()
            if (timer < 0) {
                isTestRunning.value = false
                appendMessageToLog("АВЭМ-9 не отвечает", LogType.ERROR)
                break
            }
        }

        if (isTestRunning.value) pr66.pollVoltageAKB()
        if (isTestRunning.value && pr66.lowBattery.value) {
            appendMessageToLog("Низкий заряд АВЭМ-9. Подождите немного и повторите запуск", LogType.ERROR)
            isTestRunning.value = false
        }
    }

//    fun initVibro(pol: MutableState<String>, rab: MutableState<String>) {
//        CM.startPoll(CM.DeviceID.DD2_1.name, pr102.model.VIBRO_POL) { rab.value = it.af() }
//        CM.startPoll(CM.DeviceID.DD2_1.name, pr102.model.VIBRO_RAB) { pol.value = it.af() }
//    }

//    fun checkRPM(n: MutableState<String>) {
//        if (n.value.toDouble() > testObject.specifiedRPM.toDouble() / 2) {
//            appendMessageToLog(("Waiting for engine to stop"), LogType.ERROR)
//            while (isTestRunning.value && n.value.toDouble() > testObject.specifiedRPM.toDouble() / 2) {
//                sleep(1000)
//            }
//        }
//    }

    suspend fun initARN() {
        appendMessageToLog("Инициализация ATP...", LogType.MESSAGE)
        ATR.checkResponsibility()
        delay(1000)

        if (ATR.isResponding) {
            CM.startPoll(CM.DeviceID.GV240.name, ATRModel.U_RMS_REGISTER) { value ->
                voltOnATR = value.toDouble()
                if (!ATR.isResponding) {
                    appendMessageToLog("АРН не отвечает", LogType.ERROR)
                    isTestRunning.value = false
                }
            }
            CM.startPoll(CM.DeviceID.GV240.name, ATRModel.ENDS_STATUS_REGISTER) { value ->
                latrEndsState = value.toInt()
            }
        } else {
            appendMessageToLog("АРН не отвечает", LogType.ERROR)
            isTestRunning.value = false
        }
    }

    suspend fun initPM130(vm: TestScreenViewModel) {
        appendMessageToLog("Инициализация PM130...", LogType.MESSAGE)
        parma41.checkResponsibility()

        if (isTestRunning.value) delay(1000)
        if (!parma41.isResponding) {
            appendMessageToLog("PM130 не отвечает", LogType.ERROR)
            isTestRunning.value = false
        }
        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_AB_REGISTER) { value ->
            vm.u_uv.value = (value.toDouble() * ktrVoltage).af()
            if (value.toDouble() * ktrVoltage > testObject.u_linear.toInt() * 1.1) {
                appendMessageToLog(("Overvoltage"), LogType.ERROR)
                isTestRunning.value = false
            }
            if (!parma41.isResponding && isTestRunning.value) {
                appendMessageToLog("PM130 не отвечает", LogType.ERROR)
                isTestRunning.value = false
            }
        }
        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_BC_REGISTER) { value ->
            vm.u_vw.value = (value.toDouble() * ktrVoltage).af()
            if (value.toDouble() * ktrVoltage > testObject.u_linear.toInt() * 1.1) {
                appendMessageToLog(("Overvoltage"), LogType.ERROR)
                isTestRunning.value = false
            }
        }
        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_CA_REGISTER) { value ->
            vm.u_wu.value = (value.toDouble() * ktrVoltage).af()
            voltAverage =
                (vm.u_uv.value.toDoubleOrDefault(0.0)
                        + vm.u_vw.value.toDoubleOrDefault(0.0)
                        + vm.u_wu.value.toDoubleOrDefault(0.0)) / 3 * ktrVoltage
            if (value.toDouble() * ktrVoltage > testObject.u_linear.toInt() * 1.1) {
                appendMessageToLog(("Overvoltage"), LogType.ERROR)
                isTestRunning.value = false
            }
        }
        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_A_REGISTER) {
            vm.i_u.value = (it.toDouble() * ktrAmperage).af()
        }
        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_B_REGISTER) {
            vm.i_v.value = (it.toDouble() * ktrAmperage).af()
        }
        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_C_REGISTER) {
            vm.i_w.value = (it.toDouble() * ktrAmperage).af()
        }
    }
//        if (P1 != null) {
//            CM.startPoll(CM.DeviceID.PAV41.name, PM130Model.P_REGISTER) {
//                P1.value = abs(it.toDouble() * ktrAmperage * ktrVoltage).af()
//            }
//        }
//        if (cos != null) {
//            CM.startPoll(CM.DeviceID.PAV41.name, PM130Model.COS_REGISTER) {
//                cos.value = abs(it.toDouble()).af()
//            }
//        }
//        if (F != null) {
//            CM.startPoll(CM.DeviceID.PAV41.name, PM130Model.F_REGISTER) { F.value = it.af() }
//        }


//    fun chooseCurrentStage(
//        iu: MutableState<String>,
//        iv: MutableState<String>,
//        iw: MutableState<String>
//    ) {
//        appendMessageToLog("Подбор токовой ступени", LogType.MESSAGE)
//        var Iu = iu.value.toDoubleOrDefault(100.0)
//        var Iv = iv.value.toDoubleOrDefault(100.0)
//        var Iw = iw.value.toDoubleOrDefault(100.0)
//
//        appendMessageToLog("Проверка <80А: $Iu А | $Iv А | $Iw А", LogType.MESSAGE)
//        if (Iu < 80.0 && Iv < 80.0 && Iw < 80.0) {
//            pr102.i80(true)
//            pr102.iMax250(false)
//            ktrAmperage = 80.0 / 5.0
//        }
//        if (isTestRunning.value) {
//            var timer = 5.0
//            while (isTestRunning.value && timer > 0) {
//                sleep(100)
//                timer -= 0.1
//            }
//        }
//        Iu = iu.value.toDoubleOrDefault(100.0)
//        Iv = iv.value.toDoubleOrDefault(100.0)
//        Iw = iw.value.toDoubleOrDefault(100.0)
//        appendMessageToLog("Проверка <20А: $Iu А | $Iv А | $Iw А", LogType.MESSAGE)
//        if (Iu < 20.0 && Iv < 20.0 && Iw < 20.0) {
//            pr102.i20(true)
//            pr102.i80(false)
//            ktrAmperage = 20.0 / 5.0
//        }
//        if (isTestRunning.value) {
//            var timer = 5.0
//            while (isTestRunning.value && timer > 0) {
//                Thread.sleep(100)
//                timer -= 0.1
//            }
//        }
//        Iu = iu.value.toDoubleOrDefault(100.0)
//        Iv = iv.value.toDoubleOrDefault(100.0)
//        Iw = iw.value.toDoubleOrDefault(100.0)
//        appendMessageToLog("Проверка <5А: $Iu А | $Iv А | $Iw А", LogType.MESSAGE)
//        if (Iu < 5.0 && Iv < 5.0 && Iw < 5.0) {
//            pr102.iMin(true)
//            pr102.i20(false)
//            ktrAmperage = 5.0 / 5.0
//        }
//
//    }
}