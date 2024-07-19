package ru.avem.modules.tests.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.avem.modules.common.logger.LogMessage
import ru.avem.modules.common.logger.LogType

class Logger (
//    var logMessages: SnapshotStateList<LogMessage>,
    var uiScope: CoroutineScope
) {

    val logMessages = mutableStateListOf<LogMessage>()

    fun appendMessageToLog(text: String, type: LogType = LogType.DEBUG) {
        logMessages.add(LogMessage(text, type))
//        uiScope.launch {
//            logScrollState.scrollToItem(logMessages.lastIndex)
//        }
    }
}