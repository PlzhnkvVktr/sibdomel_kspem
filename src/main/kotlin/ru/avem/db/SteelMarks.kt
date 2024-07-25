package ru.avem.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object SteelMarks : IntIdTable() {
    val name = varchar("name", 64)

    val density = varchar("density", 64)
    val losses = varchar("losses", 64)
    val intensity = varchar("intensity", 64)
    val error = varchar("error", 64)
}

class SteelMarksObjects(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SteelMarksObjects>(SteelMarks)

    var name by SteelMarks.name

    var density by SteelMarks.density
    var losses by SteelMarks.losses
    var intensity by SteelMarks.intensity
    var error by SteelMarks.error

    override fun toString(): String {
        return name
    }
}
