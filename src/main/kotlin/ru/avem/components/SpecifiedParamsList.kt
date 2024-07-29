package ru.avem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.avem.db.TestItem
import ru.avem.modules.tests.CustomController
import ru.avem.modules.tests.CustomController.testObject

@Composable
fun SpecifiedParamsList() {

    Column (
        modifier = Modifier.fillMaxHeight(0.6f).fillMaxWidth(0.12f).border(1.dp, Color.LightGray).padding(10.dp).verticalScroll(
            rememberScrollState()
        ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Номинальные параметры", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)

        SpecifiedParamsItem("Наименование", testObject.name)
        SpecifiedParamsItem("Схема", testObject.scheme.toString())
        SpecifiedParamsItem("Ток, A", testObject.i)
        SpecifiedParamsItem("Напряжение, U", testObject.u_linear)
        SpecifiedParamsItem("Мощность, P", testObject.power)

    }
}

@Composable
fun SpecifiedParamsItem (text: String, paramValue: String) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .border(1.dp, Color.LightGray)
            .padding(10.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth().background(Color.LightGray),
            textAlign = TextAlign.Center
        )
        Text(
            text = when (paramValue) {
                "true" -> "λ"
                "false" -> "∆"
                else -> paramValue
            },
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth().padding(top = 7.dp),
            textAlign = TextAlign.Center
        )
    }
}