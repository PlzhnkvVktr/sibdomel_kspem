package ru.avem.modules.common.logger


data class LogMessage(val text: String, val type: LogType)

enum class LogType  {
    ERROR,
    MESSAGE,
    DEBUG
}