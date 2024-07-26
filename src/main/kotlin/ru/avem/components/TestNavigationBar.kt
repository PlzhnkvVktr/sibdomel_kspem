package ru.avem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ru.avem.common.ProtocolBuilder
import ru.avem.db.DBManager
import ru.avem.data.enums.TestEnum
import ru.avem.modules.tests.CustomController
import ru.avem.screens.MainScreen
import ru.avem.viewmodels.MainScreenViewModel
import ru.avem.viewmodels.TestScreenViewModel
import kotlin.concurrent.thread

@Composable
fun TestNavigationBar(
    mainViewModel: MainScreenViewModel,
    testViewModel: TestScreenViewModel,
    navigator: Navigator,
    testName: TestEnum
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionButton("Отменить все", Icons.Filled.Close) {
            CustomController.isTestRunning.value = false
            DBManager.addNewProtocol(mainViewModel.factoryNumber.value)
            mainViewModel.testList.clear()
            navigator.pop()
        }
        ActionButton(
            if (!CustomController.isTestRunning.value) "Старт" else "Стоп",
            if (!CustomController.isTestRunning.value) Icons.Filled.PlayArrow else Icons.Sharp.Stop,
            testViewModel.waiting.value
        ) {
            if (!CustomController.isTestRunning.value) {
                testViewModel.start(testName)
            } else {
                CustomController.isTestRunning.value = false
            }
        }
        ActionButton(if (mainViewModel.testsLine.value.hasNext()) "Следующий" else "Завершить", Icons.AutoMirrored.Filled.ArrowForward, !CustomController.isTestRunning.value) {
            if (mainViewModel.testsLine.value.hasNext()) {
                navigator.replace(mainViewModel.testsLine.value.next())
            } else {
                mainViewModel.testList.clear()
                navigator.replace(MainScreen())
                DBManager.addNewProtocol(mainViewModel.factoryNumber.value)
                ProtocolBuilder.clear()
            }
            CustomController.logMessages.clear()
        }
    }
}