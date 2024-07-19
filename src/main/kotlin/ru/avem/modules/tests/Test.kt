package ru.avem.modules.tests

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.db.DBManager
import ru.avem.db.TestItem
import ru.avem.modules.models.SelectedTestObject
import ru.avem.viewmodels.MainScreenViewModel

abstract class Test (): Screen, MainScreenViewModel() {
    open val testName: String = ""

//    companion object {
//        val currentTestObject = mutableStateOf<SelectedTestObject?>(null)
//        val testObjectInfo = mutableStateOf<TestItem?>(null)
//        private fun getNextTestObject(
//            testItemLine: MutableState<MutableIterator<SelectedTestObject>>,
//            currentTest: MutableState<SelectedTestObject?>
//        ) {
//            currentTestObject.value = testItemLine.value.next()
//            currentTest.value = currentTestObject.value
//        }
//        private fun getTestObjectInfo() {
//            testObjectInfo.value = currentTestObject.value?.let { DBManager.getTI(it.selectedTI) }
//        }
//        fun getCurrentTestObject(
//            testItemLine: MutableState<MutableIterator<SelectedTestObject>>,
//            currentTest: MutableState<SelectedTestObject?>
//            ) {
//            getNextTestObject(testItemLine, currentTest)
//            getTestObjectInfo()
//        }
//    }


}
