package ru.avem.modules.tests.viu

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
fun VIUTestWindow (viewModel: TestScreenViewModel) {

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            TestWindowTitle(TestEnum.nameVIU.testName)
            TestWindowTitle("Измеренные значения")

            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "U, В")
                TableCell(text = "I, мА")
                TableCell(text = "t, сек")
                TableCell(text = "Результат")
            }
            Row() {
                TableCell(text = viewModel.testItem.u_viu.value)
                TableCell(text = viewModel.testItem.i_viu.value)
                TableCell(text = viewModel.testItem.t_viu.value)
                TableCell(text = viewModel.testItem.res_viu.value)
            }
        }
    }
}