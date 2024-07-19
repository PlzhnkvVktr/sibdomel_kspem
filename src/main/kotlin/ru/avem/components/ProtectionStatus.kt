package ru.avem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.avem.modules.tests.CustomController

@Composable
fun ProtectionStatusContainer () {

    Column (
        modifier = Modifier.fillMaxHeight(0.6f).border(1.dp, Color.LightGray).padding(10.dp).verticalScroll(
            rememberScrollState()
        ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Состояние защит", style = MaterialTheme.typography.h5)

        ProtectionStatusItem("Превышение тока ОИ", CustomController.ikzTI)
        ProtectionStatusItem("Токовая ВИУ", CustomController.ikzVIU)
        ProtectionStatusItem("Двери зоны", CustomController.doorZone)
        ProtectionStatusItem("Двери ШСО", CustomController.doorSCO)
    }
}


@Composable
fun ProtectionStatusItem (text: String, param: MutableState<Boolean>) {
    Card (
        modifier = Modifier.border(2.dp, Color.LightGray).size(180.dp, 100.dp),
        backgroundColor = if (param.value) Color.Red else Color.Green
    ) {
        Column (
            modifier = Modifier
                .fillMaxHeight(0.6f)
//                .border(1.dp, Color.LightGray)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
//                modifier = Modifier.fillMaxWidth().background(Color.LightGray),
                textAlign = TextAlign.Center
            )
        }
    }
}