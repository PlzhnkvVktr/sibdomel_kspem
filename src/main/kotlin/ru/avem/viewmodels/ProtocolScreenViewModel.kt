package ru.avem.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.avem.common.ProtocolBuilder
import ru.avem.db.DBManager
import ru.avem.db.TestProtocol
import ru.avem.utils.excel.saveProtocolAsWorkbook
import java.awt.Desktop
import java.io.File
import java.io.IOException

class ProtocolScreenViewModel : ScreenModel {
    var protocolList = mutableStateListOf<TestProtocol>()
    var textFind = mutableStateOf("")

    private fun openFile(file: File) {
        try {
            Desktop.getDesktop().open(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getProtocols() {
        screenModelScope.launch {
            protocolList.clear()
            protocolList.addAll(DBManager.getAllProtocols())
        }
    }

    fun performSearch(predicate: String) {
        screenModelScope.launch {
            val values = DBManager.findProtocol(predicate)
            protocolList.clear()
            protocolList.addAll(values)
        }
    }

    fun deleteProtocol(protocol: TestProtocol) {
        screenModelScope.launch {
            DBManager.deleteProtocolItemByName1(protocol)
            protocolList.remove(protocol)
        }
    }

//    fun savePDF (protocol: TestProtocol) {
//        scope.launch {
//            ProtocolBuilder.fillProtocol(protocol)
//            PdfCreator().createPDF(ProtocolBuilder)
//            openFile(File("./report.pdf"))
//            ProtocolBuilder.clear()
//        }
//    }

    fun saveExcel (protocol: TestProtocol) {
        screenModelScope.launch {
            ProtocolBuilder.fillProtocol(protocol)
            saveProtocolAsWorkbook(ProtocolBuilder)
            openFile(File("cfg/lastOpened.xlsx"))
            ProtocolBuilder.clear()
        }
    }

}