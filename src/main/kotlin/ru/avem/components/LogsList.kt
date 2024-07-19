package ru.avem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.avem.modules.common.logger.LogType
import ru.avem.modules.tests.CustomController.logMessages


@Composable
fun LogsList(screenModelScope: CoroutineScope) {
    val logScrollState = rememberLazyListState()

    ScrollableLazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(200.dp)
            .border(2.dp, Color.DarkGray),
        logScrollState
    ) {
        items(logMessages.size) { index ->
            Text(
                text = logMessages[index].text,
                modifier = Modifier.padding(3.dp).fillMaxWidth(),
                style = MaterialTheme.typography.h5,
                color =
                when (logMessages[index].type) {
                    LogType.ERROR -> MaterialTheme.colors.error
                    LogType.MESSAGE -> MaterialTheme.colors.primaryVariant
                    LogType.DEBUG -> MaterialTheme.colors.secondary
                }
            )
        }
        screenModelScope.launch {
            logScrollState.scrollToItem(logMessages.size)
        }
    }
}