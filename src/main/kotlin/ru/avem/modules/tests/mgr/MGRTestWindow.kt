package ru.avem.modules.tests.mgr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.components.TableCell
import ru.avem.components.TestWindowTitle
import ru.avem.data.enums.TestEnum
import ru.avem.viewmodels.TestScreenViewModel

@Composable
fun MGRTestWindow (viewModel: TestScreenViewModel) {

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            TestWindowTitle(TestEnum.nameMGR.testName)
            TestWindowTitle("Номинальные параметры")

            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "U номинальное")
            }
            Row() {
                TableCell(text = viewModel.testItem.specifiedMgrU.value)
            }

            TestWindowTitle("Измеренные значения")

            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "U")
                TableCell(text = "R15, Ом")
                TableCell(text = "R60, Ом")
                TableCell(text = "Abs")
            }
            Row() {
                TableCell(text = viewModel.testItem.mgrU.value)
                TableCell(text = viewModel.testItem.r15.value)
                TableCell(text = viewModel.testItem.r60.value)
                TableCell(text = viewModel.testItem.kABS.value)
            }

        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.background(Color.Gray).fillMaxWidth(0.25f)) {
                TableCell(text = "Результат")
            }

            Row(Modifier.fillMaxWidth(0.25f)) {
                TableCell(text = viewModel.result.value)
            }
        }
    }
}