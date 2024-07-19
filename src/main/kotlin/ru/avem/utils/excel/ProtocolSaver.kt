package ru.avem.utils.excel

import androidx.compose.ui.res.useResource
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.avem.common.ProtocolBuilder
import ru.avem.utils.copyFileFromStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths

val sp = File.separatorChar

fun saveProtocolAsWorkbook(protocol: ProtocolBuilder, path: String = "cfg/lastOpened.xlsx") {
    if(!File("cfg").exists()){
        Files.createDirectories(Paths.get("cfg"))
    }
    val template = File(path)
    if (File("protocol.xlsx").exists()) {
        copyFileFromStream(File("protocol.xlsx").inputStream(), template)
    } else {
        useResource("protocol.xlsx") {
            copyFileFromStream(it, File("protocol.xlsx"))
        }
        copyFileFromStream(File("protocol.xlsx").inputStream(), template)
    }

    try {
        XSSFWorkbook(template).use { wb ->
            val sheet = wb.getSheetAt(0)
            for (iRow in 0 until 150) {
                val row = sheet.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 150) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {

                            
                            when (cell.stringCellValue) {
                                "#TESTITEM_NAME#" -> cell.setCellValue(protocol.name)

                                "#IDTEST#" -> cell.setCellValue(protocol.id)
                                "#OPERATOR#" -> cell.setCellValue(protocol.operator)
                                "#V1#" -> cell.setCellValue(("vibroJ"))
                                "#V2#" -> cell.setCellValue(("vibroF"))
                                "#T1#" -> cell.setCellValue(("t"))
                                "#T2#" -> cell.setCellValue(("tI"))
                                "#VIUNAME#" -> cell.setCellValue(("viu"))
                                "#MGRNAME#" -> cell.setCellValue(("mgr"))
                                "#IKASNAME#" -> cell.setCellValue(("ikas"))
                                "#HHNAME#" -> cell.setCellValue(("hh"))
                                "#KZNAME#" -> cell.setCellValue(("kz"))
                                "#TRNAME#" -> cell.setCellValue(("trRatio"))
                                "#MVNAME#" -> cell.setCellValue(("mv"))
                                "#RUNNINGNAME#" -> cell.setCellValue(("n"))
                                "#RESULT#" -> cell.setCellValue(("res"))
                                "#BEFORE#" -> cell.setCellValue(("values before"))
                                "#AFTER#" -> cell.setCellValue(("values after"))
                                "#DEVIATION#" -> cell.setCellValue(("deviation"))
                                "#STATOR#" -> cell.setCellValue(("stator"))
                                "#ROTOR#" -> cell.setCellValue(("rotor"))
                                "#OPERATORTITLE#" -> cell.setCellValue(("Operator"))
                                "#DATETITLE#" -> cell.setCellValue(("Date"))
                                "#TIMETITLE#" -> cell.setCellValue(("Time"))
                                "#STARTPARAM#" -> cell.setCellValue(("set values"))
                                "#RECOMENDATION#" -> cell.setCellValue(("recommendations"))

                                "#FN#" -> cell.setCellValue(("Factory number"))
                                "#TO#" -> cell.setCellValue(("test object protocol"))
                                "#TT#" -> cell.setCellValue(("tt"))
                                "#TE#" -> cell.setCellValue(("Test equipment"))
                                "#PN#" -> cell.setCellValue(("Protocol number"))
                                "#RSM#" -> cell.setCellValue(("Rotation speed"))
                                "#DU#" -> cell.setCellValue(("d Iu"))
                                "#DV#" -> cell.setCellValue(("d Iv"))
                                "#DW#" -> cell.setCellValue(("d Iw"))

                                "#DATE#" -> cell.setCellValue(protocol.date)
                                "#TIME#" -> cell.setCellValue(protocol.time)
                                "#SERIAL#" -> cell.setCellValue(protocol.type)
                                "#P2#" -> cell.setCellValue(protocol.specifiedP)
                                "#UN#" -> cell.setCellValue(protocol.specifiedU)
                                "#IN#" -> cell.setCellValue(protocol.specifiedI)
                                "#NASYNC#" -> cell.setCellValue(protocol.specifiedRPM)
                                "#SCHEMETITLE#" -> cell.setCellValue(("scheme"))
                                "#SCHEME#" -> cell.setCellValue(if (protocol.scheme.toBoolean()) "∆" else "λ")
                                //MGR
//                                "#MGRNAME#" -> cell.setCellValue(protocol.R15)
                                "#MGRU#" -> cell.setCellValue(protocol.u_1)
                                "#MGRR15#" -> cell.setCellValue(protocol.r15_1)
                                "#MGRR60#" -> cell.setCellValue(protocol.r60_1)
                                "#MGRKABS#" -> cell.setCellValue(protocol.kABS_1)
                                "#MGRTEMP#" -> cell.setCellValue(protocol.mgrT_1)
                                "#MGRRESULT#" -> cell.setCellValue(protocol.mgrResult_1)

                                "#MGRU#" -> cell.setCellValue(protocol.u_2)
                                "#MGRR15#" -> cell.setCellValue(protocol.r15_2)
                                "#MGRR60#" -> cell.setCellValue(protocol.r60_2)
                                "#MGRKABS#" -> cell.setCellValue(protocol.kABS_2)
                                "#MGRTEMP#" -> cell.setCellValue(protocol.mgrT_2)
                                "#MGRRESULT#" -> cell.setCellValue(protocol.mgrResult_2)

                                "#MGRU#" -> cell.setCellValue(protocol.u_3)
                                "#MGRR15#" -> cell.setCellValue(protocol.r15_3)
                                "#MGRR60#" -> cell.setCellValue(protocol.r60_3)
                                "#MGRKABS#" -> cell.setCellValue(protocol.kABS_3)
                                "#MGRTEMP#" -> cell.setCellValue(protocol.mgrT_3)
                                "#MGRRESULT#" -> cell.setCellValue(protocol.mgrResult_3)
//                                //VIU
//                                "#VIUNAME#" -> cell.setCellValue(protocol.R15)
//                                "#VIUU#" -> cell.setCellValue(protocol.viuU)
//                                "#VIUI#" -> cell.setCellValue(protocol.viuI)
//                                "#VIUTIME#" -> cell.setCellValue(protocol.viuTime)
//                                "#VIURESULT#" -> cell.setCellValue(protocol.viuResult)
//                                //IKAS
//                                "#IKASDEV#" -> cell.setCellValue(protocol.deviationIkas)
//                                "#IKASR1#" -> cell.setCellValue(protocol.Ruv1)
//                                "#IKASR2#" -> cell.setCellValue(protocol.Rvw1)
//                                "#IKASR3#" -> cell.setCellValue(protocol.Rwu1)
//                                "#IKASR11#" -> cell.setCellValue(protocol.Ruv2)
//                                "#IKASR21#" -> cell.setCellValue(protocol.Rvw2)
//                                "#IKASR31#" -> cell.setCellValue(protocol.Rwu2)
//                                "#IKASRESULT#" -> cell.setCellValue(protocol.ikasResult)
//                                //HH
//                                "#HHNAME#" -> cell.setCellValue(protocol.R15)
//                                "#HHUAB#" -> cell.setCellValue(protocol.hhUAB)
//                                "#HHUBC#" -> cell.setCellValue(protocol.hhUBC)
//                                "#HHUCA#" -> cell.setCellValue(protocol.hhUCA)
//                                "#HHUOV#" -> cell.setCellValue(protocol.hhUOV)
//                                "#HHIA#" -> cell.setCellValue(protocol.hhIA)
//                                "#HHIB#" -> cell.setCellValue(protocol.hhIB)
//                                "#HHIC#" -> cell.setCellValue(protocol.hhIC)
//                                "#HHIOV#" -> cell.setCellValue(protocol.hhIOV)
//                                "#HHTEMPTI#" -> cell.setCellValue(protocol.hhTempTI)
//                                "#HHTEMPAMB#" -> cell.setCellValue(protocol.hhTempAmb)
//                                "#HHSPEED#" -> cell.setCellValue(protocol.hhSpeed)
//                                "#HHVIBRO1#" -> cell.setCellValue(protocol.hhVibro1)
//                                "#HHVIBRO2#" -> cell.setCellValue(protocol.hhVibro2)
//                                "#HHTIME#" -> cell.setCellValue(protocol.hhTime)
//                                "#HHP1#" -> cell.setCellValue(protocol.hhP1)
//                                "#HHCOS#" -> cell.setCellValue(protocol.hhCos)
//                                "#HHRESULT#" -> cell.setCellValue(protocol.hhResult)
//                                //TR
//                                "#TRNAME#" -> cell.setCellValue(protocol.R15)
//                                "#TRUAB#" -> cell.setCellValue(protocol.trUAB)
//                                "#TRUBC#" -> cell.setCellValue(protocol.trUBC)
//                                "#TRUCA#" -> cell.setCellValue(protocol.trUCA)
//
//                                "#TRUAB1#" -> cell.setCellValue(protocol.trUAB1)
//                                "#TRUBC1#" -> cell.setCellValue(protocol.trUBC1)
//                                "#TRUCA1#" -> cell.setCellValue(protocol.trUCA1)
//                                "#TRUAB2#" -> cell.setCellValue(protocol.trUAB2)
//                                "#TRUBC2#" -> cell.setCellValue(protocol.trUBC2)
//                                "#TRUCA2#" -> cell.setCellValue(protocol.trUCA2)
//                                "#TRCALCUAB#" -> cell.setCellValue(protocol.trCalcUAB)
//                                "#TRCALCUBC#" -> cell.setCellValue(protocol.trCalcUBC)
//                                "#TRCALCUCA#" -> cell.setCellValue(protocol.trCalcUCA)

//                                "#TRUOV#" -> cell.setCellValue(protocol.trUOV)
//                                "#TRIA#" -> cell.setCellValue(protocol.trIA)
//                                "#TRIB#" -> cell.setCellValue(protocol.trIB)
//                                "#TRIC#" -> cell.setCellValue(protocol.trIC)
//                                "#TRIOV#" -> cell.setCellValue(protocol.trIOV)
//                                "#TRTEMPTI#" -> cell.setCellValue(protocol.trTempTI)
//                                "#TRTEMPAMB#" -> cell.setCellValue(protocol.trTempAmb)
//                                "#TRSPEED#" -> cell.setCellValue(protocol.trSpeed)
//                                "#TRVIBRO1#" -> cell.setCellValue(protocol.trVibro1)
//                                "#TRVIBRO2#" -> cell.setCellValue(protocol.trVibro2)
////                                "#TRTIME#" -> cell.setCellValue(protocol.trTime)
//                                "#TRP1#" -> cell.setCellValue(protocol.trP1)
//                                "#TRCOS#" -> cell.setCellValue(protocol.trCos)
//                                "#TRRESULT#" -> cell.setCellValue(protocol.trResult)
//                                //KZ
//                                "#KZNAME#" -> cell.setCellValue(protocol.R15)
//                                "#KZUAB#" -> cell.setCellValue(protocol.kzUAB)
//                                "#KZUBC#" -> cell.setCellValue(protocol.kzUBC)
//                                "#KZUCA#" -> cell.setCellValue(protocol.kzUCA)
//                                "#KZUOV#" -> cell.setCellValue(protocol.kzUOV)
//                                "#KZIA#" -> cell.setCellValue(protocol.kzIA)
//                                "#KZIB#" -> cell.setCellValue(protocol.kzIB)
//                                "#KZIC#" -> cell.setCellValue(protocol.kzIC)
//                                "#KZIOV#" -> cell.setCellValue(protocol.kzIOV)
//                                "#KZTEMPTI#" -> cell.setCellValue(protocol.kzTempTI)
//                                "#KZTEMPAMB#" -> cell.setCellValue(protocol.kzTempAmb)
//                                "#KZSPEED#" -> cell.setCellValue(protocol.kzSpeed)
//                                "#KZVIBRO1#" -> cell.setCellValue(protocol.kzVibro1)
//                                "#KZVIBRO2#" -> cell.setCellValue(protocol.kzVibro2)
////                                "#KZTIME#" -> cell.setCellValue(protocol.kzTime)
//                                "#KZP1#" -> cell.setCellValue(protocol.kzP1)
//                                "#KZCOS#" -> cell.setCellValue(protocol.kzCos)
//                                "#KZRESULT#" -> cell.setCellValue(protocol.kzResult)
//                                //N
//                                "#NNAME#" -> cell.setCellValue(protocol.R15)
//                                "#NUAB#" -> cell.setCellValue(protocol.idleUAB)
//                                "#NUBC#" -> cell.setCellValue(protocol.idleUBC)
//                                "#NUCA#" -> cell.setCellValue(protocol.idleUCA)
//                                "#NUOV#" -> cell.setCellValue(protocol.idleUOV)
//                                "#NIA#" -> cell.setCellValue(protocol.idleIA)
//                                "#NIB#" -> cell.setCellValue(protocol.idleIB)
//                                "#NIC#" -> cell.setCellValue(protocol.idleIC)
//                                "#NVIBRO1#" -> cell.setCellValue(protocol.idleVibro1)
//                                "#NVIBRO2#" -> cell.setCellValue(protocol.idleVibro2)
////                                "#NIOV#" -> cell.setCellValue(protocol.idle)
//                                "#NSPEED#" -> cell.setCellValue(protocol.idleSpeed)
//                                "#NF#" -> cell.setCellValue(protocol.idleF)
//                                "#NRESULT#" -> cell.setCellValue(protocol.idleResult)
//                                "#NCOS#" -> cell.setCellValue(protocol.idleCos)
//                                "#NP1#" -> cell.setCellValue(protocol.idleP1)
//                                "#NTEMPTI#" -> cell.setCellValue(protocol.idleTempTI)
//                                "#NTEMPAMB#" -> cell.setCellValue(protocol.idleTempAmb)
//                                "#NTIME#" -> cell.setCellValue(protocol.idleTime)
//                                //MV
//
//
//                                "#MVUAB1#" -> cell.setCellValue(protocol.beforeUuv)
//                                "#MVUBC1#" -> cell.setCellValue(protocol.beforeUvw)
//                                "#MVUCA1#" -> cell.setCellValue(protocol.beforeUwu)
//                                "#MVUAB2#" -> cell.setCellValue(protocol.afterUuv)
//                                "#MVUBC2#" -> cell.setCellValue(protocol.afterUvw)
//                                "#MVUCA2#" -> cell.setCellValue(protocol.afterUwu)
////                                "#MVCALC#" -> cell.setCellValue(protocol.de)
//                                "#MVIA1#" -> cell.setCellValue(protocol.beforeIu)
//                                "#MVIB1#" -> cell.setCellValue(protocol.beforeIv)
//                                "#MVIC1#" -> cell.setCellValue(protocol.beforeIw)
//                                "#MVIA2#" -> cell.setCellValue(protocol.afterIu)
//                                "#MVIB2#" -> cell.setCellValue(protocol.afterIv)
//                                "#MVIC2#" -> cell.setCellValue(protocol.afterIw)
//                                "#MVRESULT#" -> cell.setCellValue(protocol.mvResult)
//                                "#MVCALCA#" -> cell.setCellValue(protocol.mvCalcA)
//                                "#MVCALCB#" -> cell.setCellValue(protocol.mvCalcB)
//                                "#MVCALCC#" -> cell.setCellValue(protocol.mvCalcC)
                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: FileNotFoundException) {
//        errorNotification(
//            ("error"),
//            "Не удалось сохранить протокол на диск",
//            Pos.BOTTOM_CENTER
//        )
        println("ssssss")
    }
}
