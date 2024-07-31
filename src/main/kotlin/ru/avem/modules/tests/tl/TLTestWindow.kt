package ru.avem.modules.tests.tl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun TLTestWindow (viewModel: TestScreenViewModel) {

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            TestWindowTitle(TestEnum.nameTL.testName)
            TestWindowTitle("Измеренные значения")
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Заданное U, В")
                TableCell(text = "Измеренное U, В")
                TableCell(text = "Ток, А")
                TableCell(text = "P измеренная, Вт")
            }
            Row() {
                TableCell(text = viewModel.testItem.u_set_tl.value)
                TableCell(text = viewModel.u_a.value)
                TableCell(text = viewModel.i_u.value)
                TableCell(text = viewModel.pA.value)
            }
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Индукция, Тл")
                TableCell(text = "P стали приведенная к 1 Тл, Вт")
                TableCell(text = "Напряженность, А/м")
                TableCell(text = "Удельные потери, Вт/кг")
            }
            Row() {
                TableCell(text = viewModel.testItem.induction_tl.value)
                TableCell(text = viewModel.testItem.p_steel_tl.value)
                TableCell(text = viewModel.testItem.intensity_tl.value)
                TableCell(text = viewModel.testItem.losses_tl.value)
            }
        }

        Column (
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.background(Color.Gray).fillMaxWidth(0.5f)) {
                TableCell(text = "Результат")
            }
            Row(Modifier.fillMaxWidth(0.5f)) {
                TableCell(text = viewModel.testItem.res_tl.value)
            }
        }
    }
}