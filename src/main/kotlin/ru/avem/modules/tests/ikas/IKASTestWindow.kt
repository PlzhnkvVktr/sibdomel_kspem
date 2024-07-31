package ru.avem.modules.tests.ikas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.common.af
import ru.avem.components.TableCell
import ru.avem.components.TestWindowTitle
import ru.avem.data.enums.TestEnum
import ru.avem.viewmodels.TestScreenViewModel

@Composable
fun IKASTestWindow (viewModel: TestScreenViewModel) {

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column (
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            TestWindowTitle(TestEnum.nameIKAS.testName)
            TestWindowTitle("Измеренные значения")

            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "R uv, Ом")
                TableCell(text = "R vw, Ом")
                TableCell(text = "R wu, Ом")
            }
            Row() {
                TableCell(text = viewModel.testItem.r_uv_ikas.value)
                TableCell(text = viewModel.testItem.r_vw_ikas.value)
                TableCell(text = viewModel.testItem.r_wu_ikas.value)
            }
            TestWindowTitle("Фазное")


            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "R u, Ом")
                TableCell(text = "R v, Ом")
                TableCell(text = "R w, Ом")
            }
            Row() {
                TableCell(text = viewModel.testItem.calc_u_ikas.value)
                TableCell(text = viewModel.testItem.calc_v_ikas.value)
                TableCell(text = viewModel.testItem.calc_v_ikas.value)
            }

        }

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.background(Color.Gray).fillMaxWidth(0.5f)) {
                TableCell(text = "Разница, %")
                TableCell(text = "Результат")
            }

            Row(Modifier.fillMaxWidth(0.5f)) {
                TableCell(text = viewModel.testItem.deviation.value.af())
                TableCell(text = viewModel.testItem.res_ikas.value)
            }
        }

    }


}