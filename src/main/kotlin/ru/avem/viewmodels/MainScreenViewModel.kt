package ru.avem.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import ru.avem.db.DBManager
import ru.avem.data.enums.TestEnum
import ru.avem.modules.models.SelectedTestObject
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.Test
import ru.avem.modules.tests.hh.HHandMVZScreen
import ru.avem.modules.tests.ikas.IKASScreen
import ru.avem.modules.tests.mgr.MGRScreen
import ru.avem.modules.tests.tl.TLScreen
import ru.avem.modules.tests.viu.VIUScreen

open class MainScreenViewModel : ScreenModel {

    var startTestButton: MutableState<Boolean> = mutableStateOf(false)
    var allCheckedButton: MutableState<Boolean> = mutableStateOf(false)

    var testList = mutableListOf<Test>()
    var testsLine = mutableStateOf(testList.iterator())

    private var testItemList = mutableListOf<SelectedTestObject>()
    var testItemLine = mutableStateOf(testItemList.iterator())

    val testMap: MutableMap<TestEnum, MutableState<Boolean>> = mutableMapOf(
        TestEnum.nameMGR to mutableStateOf(false),
        TestEnum.nameVIU to mutableStateOf(false),
        TestEnum.nameIKAS to mutableStateOf(false),
        TestEnum.nameHHandMVZ to mutableStateOf(false),
        TestEnum.nameTL to mutableStateOf(false)
    )

    var typesTI = DBManager.getAllTI().ifEmpty { listOf("") }
    var selectedTI = mutableStateOf(typesTI.first())
    var factoryNumber = mutableStateOf("")

    private fun checkTest (item: TestEnum): Boolean {
        return when (item) {
            TestEnum.nameMGR -> testList.add(MGRScreen(this))
            TestEnum.nameVIU -> testList.add(VIUScreen(this))
            TestEnum.nameIKAS -> testList.add(IKASScreen(this))
            TestEnum.nameHHandMVZ -> testList.add(HHandMVZScreen(this))
            TestEnum.nameTL -> testList.add(TLScreen(this))
        }
    }

    fun checkboxClick (
        item: Map.Entry<TestEnum, MutableState<Boolean>>,
        found: Test?
    ) {
        if (found != null) {
            testList.remove(found)
        } else {
            checkTest(item.key)
        }

        testsLine.value = testList.iterator()
        startTestButton.value = testsLine.value.hasNext()
    }
    fun selectAll () {
        screenModelScope.launch {
            if (!allCheckedButton.value) {
                allCheckedButton.value = true
                testList.clear()
                testMap.forEach { item ->
                    item.value.value = true
                    checkTest(item.key)
                }
            } else {
                allCheckedButton.value = false
                testMap.forEach { item -> item.value.value = false }
                testList.clear()
            }
            testsLine.value = testList.iterator()
            startTestButton.value = testsLine.value.hasNext()
        }
    }

    fun startTests (navigator: Navigator) {
        screenModelScope.launch {
            val a = java.util.Date().time
            CustomController.testObjectName.value = selectedTI.value
            CustomController.testObject = DBManager.getTI(CustomController.testObjectName.value)
            navigator.push(testsLine.value.next())
            testMap.forEach { item -> item.value.value = false }
            startTestButton.value = false
            allCheckedButton.value = false
            val b = java.util.Date().time
            println("====> ${a - b}")
        }
    }

    fun clearTestItemList () {
        testItemList.clear()
        testItemLine.value = testItemList.iterator()
    }


}
