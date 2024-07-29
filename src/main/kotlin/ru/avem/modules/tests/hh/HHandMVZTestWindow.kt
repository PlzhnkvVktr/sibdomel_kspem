package ru.avem.modules.tests.hh

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
fun HHandMVZTestWindow (viewModel: TestScreenViewModel) {

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            TestWindowTitle(TestEnum.nameHHandMVZ.testName)
            TestWindowTitle("Измеренные значения")
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "U uv, B")
                TableCell(text = "U vw, B")
                TableCell(text = "U wu, B")
                TableCell(text = "I u, A")
                TableCell(text = "I v, A")
                TableCell(text = "I w, A")
            }
            Row() {
                TableCell(text = viewModel.u_uv.value)
                TableCell(text = viewModel.u_vw.value)
                TableCell(text = viewModel.u_wu.value)
                TableCell(text = viewModel.i_u.value)
                TableCell(text = viewModel.i_v.value)
                TableCell(text = viewModel.i_w.value)
            }

//            Row(Modifier.background(Color.Gray)) {
//                TableCell(text = "U uv, B")
//                TableCell(text = "U vw, B")
//                TableCell(text = "U wu, B")
//                TableCell(text = "I u, A")
//                TableCell(text = "I v, A")
//                TableCell(text = "I w, A")
//            }
//            Row() {
//                TableCell(text = viewModel.testItem.u_uv_hh.value)
//                TableCell(text = viewModel.testItem.u_vw_hh.value)
//                TableCell(text = viewModel.testItem.u_wu_hh.value)
//                TableCell(text = viewModel.testItem.i_u_hh.value)
//                TableCell(text = viewModel.testItem.i_v_hh.value)
//                TableCell(text = viewModel.testItem.i_w_hh.value)
//            }

        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.background(Color.Gray).fillMaxWidth(0.5f)) {
                TableCell(text = "Отклонение токов, %")
                TableCell(text = "Результат")
            }
            Row(Modifier.fillMaxWidth(0.5f)) {
                TableCell(text = viewModel.testItem.i_deviation_hh.value)
                TableCell(text = viewModel.testItem.hhResult.value)
            }
        }
    }
}