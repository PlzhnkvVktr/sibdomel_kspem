package ru.avem.common.repos

@kotlinx.serialization.Serializable
data class Settings(
    var language: String = "",
    var login: String = "",
    var font: String = "",
    var isAdmin: Boolean = true
)