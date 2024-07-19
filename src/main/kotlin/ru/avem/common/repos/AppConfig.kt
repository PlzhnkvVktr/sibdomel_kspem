package ru.avem.common.repos

import androidx.compose.runtime.mutableStateOf
import ru.avem.common.loadFromJson
import ru.avem.common.saveToJsonFile
import ru.avem.db.User
import java.nio.file.Paths

object AppConfig {
//    var language = mutableStateOf(!cfg.readText().contains("RU"))
    lateinit var params: Settings

    fun update(user: User) {
        params.login = user.login
        params.isAdmin = user.isAdmin
        save()
    }

    fun save() {
        saveToJsonFile(
            Paths.get("device.json"),
            params
        )
    }

    init {
        try {
            params = loadFromJson(Paths.get("device.json"))
        } catch (e: Exception) {
            params =
                Settings(
                    language = "RU",
                    font = "14",
                    isAdmin = true
                )
            saveToJsonFile(
                Paths.get("device.json"),
                params
            )
        }
    }

}
