package ru.avem.modules.tests.ikas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.common.af
import ru.avem.components.TableCell
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
            Row (modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.Center) {
                Text(text = viewModel.nameTest, style = MaterialTheme.typography.h5)
            }

            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Измеренные значения")
                TableCell(text = "R UV, Ом")
                TableCell(text = "R VW, Ом")
                TableCell(text = "R WU, Ом")
            }

            Column (
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Row(Modifier.background(Color.Gray)) {
                    TableCell(text = "Приведенные к 20 °C")
                    TableCell(text = "R U, Ом")
                    TableCell(text = "R V, Ом")
                    TableCell(text = "R W, Ом")
                    TableCell(text = "Разница")
                }

            }
            Column (
                modifier = Modifier.padding(top = 20.dp)
            ) {
//                Row(Modifier.background(Color.Gray).fillMaxWidth(0.75f)) {
//                    TableCell(text = "t")
//                    TableCell(text = "tI")
//                    TableCell(text = "Разница")
//                }
//
//                Row(Modifier.fillMaxWidth(0.75f)) {
//                    TableCell(text = viewModel.tempTI.value)
//                    TableCell(text = viewModel.tempAmb.value)
//                    TableCell(text = viewModel.deviation.value.af())
//                }
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