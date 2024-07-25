package ru.avem.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object TestItems : IntIdTable() {
    var name = varchar("name", 50)
    val scheme = bool("scheme")
    val power = varchar("power", 20)
    val u_linear = varchar("u_linear", 20)
    val i = varchar("i", 20)
    val i_viu = varchar("i_viu", 20)
    val i_mz = varchar("i_mz", 20)
    val u_viu = varchar("u_viu", 20)
    val u_mgr = varchar("u_mgr", 20)
    val t_viu = varchar("t_viu", 20)
    val t_hh = varchar("t_hh", 20)
    val t_mv = varchar("t_mv", 20)
    val r_max = varchar("r_max", 20)
    val r_min = varchar("r_min", 20)
    val r20_max = varchar("r20_max", 20)
    val r20_min = varchar("r20_min", 20)
    val steel_mark = varchar("steel_mark", 20)
    val isolation = bool("intensity")
    val d_inside = varchar("d_inside", 20)
    val d_outer = varchar("d_outer", 20)
    val stator_length = varchar("stator_length", 20)
    val height_slot = varchar("height_slot", 20)
    val material = bool("material")
}

class TestItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TestItem>(TestItems)

    var name by TestItems.name
    var scheme by TestItems.scheme
    var power by TestItems.power
    var u_linear by TestItems.u_linear
    var i by TestItems.i
    var i_viu by TestItems.i_viu
    var i_mz by TestItems.i_mz
    var u_viu by TestItems.u_viu
    var u_mgr by TestItems.u_mgr
    var t_viu by TestItems.t_viu
    var t_hh by TestItems.t_hh
    var t_mv by TestItems.t_mv
    var r_max by TestItems.r_max
    var r_min by TestItems.r_min
    var r20_max by TestItems.r20_max
    var r20_min by TestItems.r20_min
    var steel_mark by TestItems.steel_mark
    var isolation by TestItems.isolation
    var d_inside by TestItems.d_inside
    var d_outer by TestItems.d_outer
    var stator_length by TestItems.stator_length
    var height_slot by TestItems.height_slot
    var material by TestItems.material

    override fun toString() = name

}