package ru.avem.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import ru.avem.db.TestProtocols.nullable
import kotlin.reflect.full.memberProperties

object TestProtocols : IntIdTable() {
//    fun a() {
//        this::class.memberProperties.forEach {
//            println(it.getter.call())
//        }
//    }

    var name = varchar("name", 64)
    var type = varchar("type", 64).nullable()
    var operator = varchar("operator", 256).nullable()
    var time = varchar("time", 64).nullable()
    var date = varchar("date", 64).nullable()

    val specifiedP = varchar("specifiedP", 64).nullable()
    var specifiedU = varchar("specifiedU", 64).nullable()
    val specifiedI = varchar("specifiedI", 64).nullable()
    val specifiedRPM = varchar("specifiedRPM", 64).nullable()
    val specifiedIdleTime = varchar("specifiedIdleTime", 64).nullable()
    var specifiedRunningTime = varchar("specifiedRunningTime", 64).nullable()
    val specifiedMgrU = varchar("specifiedMgrU", 64).nullable()
    val specifiedViuU = varchar("specifiedViuU", 64).nullable()
    val scheme = bool("scheme").nullable()

    //MGR мегер
    val mgrU = varchar("U", 10).nullable()
    var mgrR15 = varchar("R15", 10).nullable()
    val mgrR60 = varchar("R60", 10).nullable()
    val mgrkABS = varchar("kABS", 10).nullable()
    var mgrT = varchar("mgrT", 10).nullable()
    var mgrResult = varchar("mgrResult", 240).nullable()

    //IKAS Измерение сопротивления обмоток постоянному току в практически холодном состоянии

    val ikasRuv = varchar("Ruv", 10).nullable()
    val ikasRvw = varchar("Rvw", 10).nullable()
    val ikasRwu = varchar("Rwu", 10).nullable()
    val calc_u_ikas = varchar("calc_u_ikas", 10).nullable()
    val calc_v_ikas = varchar("calc_v_ikas", 10).nullable()
    val calc_w_ikas = varchar("calc_w_ikas", 10).nullable()
    val deviation = varchar("deviation", 10).nullable()
    var ikasResult = varchar("ikasResult", 240).nullable()

    //VIU Испытание изоляции обмоток относительно корпуса и между фазами на электрическую прочность

    var u_viu = varchar("u_viu", 10).nullable()
    var i_viu = varchar("i_viu", 10).nullable()
    var t_viu = varchar("t_viu", 10).nullable()
    var viuResult = varchar("viuResult", 240).nullable()

    //MVZ HH
    var u_uv_hh = varchar("u_uv_hh", 10).nullable()
    var u_vw_hh = varchar("u_vw_hh", 10).nullable()
    var u_wu_hh = varchar("u_wu_hh", 10).nullable()
    var i_u_hh = varchar("i_u_hh", 10).nullable()
    var i_v_hh = varchar("i_v_hh", 10).nullable()
    var i_w_hh = varchar("i_w_hh", 10).nullable()
    var cos_hh = varchar("cos_hh", 10).nullable()
    var hhResult = varchar("hhResult", 240).nullable()

    //TL Определение тока и потерь ХХ
    var u_set_tl = varchar("u_set_tl", 10).nullable()
    var u_izm_tl = varchar("u_izm_tl", 10).nullable()
    var i_u_tl = varchar("i_u_tl", 10).nullable()
    var p_tl = varchar("p_tl", 10).nullable()
    var induction_tl = varchar("induction_tl", 10).nullable()
    var p_steel_tl = varchar("p_steel_tl", 10).nullable()
    var intensity_tl = varchar("intensity_tl", 10).nullable()
    var losses_tl = varchar("losses_tl", 10).nullable()
    var tlResult = varchar("tlResult", 240).nullable()


}

class TestProtocol(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TestProtocol>(TestProtocols)

    var name by TestProtocols.name
    var type by TestProtocols.type
    var operator by TestProtocols.operator
    var time by TestProtocols.time
    var date by TestProtocols.date

    var specifiedP by TestProtocols.specifiedP
    var specifiedU by TestProtocols.specifiedU
    var specifiedI by TestProtocols.specifiedI
    var specifiedRPM by TestProtocols.specifiedRPM
    var specifiedIdleTime by TestProtocols.specifiedIdleTime
    var specifiedRunningTime by TestProtocols.specifiedRunningTime
    var specifiedMgrU by TestProtocols.specifiedMgrU
    var specifiedViuU by TestProtocols.specifiedViuU
    var scheme by TestProtocols.scheme

    //MGR
    var mgrU by TestProtocols.mgrU
    var mgrR15 by TestProtocols.mgrR15
    var mgrR60 by TestProtocols.mgrR60
    var mgrkABS by TestProtocols.mgrkABS
    var mgrResult by TestProtocols.mgrResult


    //IKAS
    var ikasRuv by TestProtocols.ikasRuv
    var ikasRvw by TestProtocols.ikasRvw
    var ikasRwu by TestProtocols.ikasRwu
    var calc_u_ikas by TestProtocols.calc_u_ikas
    var calc_v_ikas by TestProtocols.calc_v_ikas
    var calc_w_ikas by TestProtocols.calc_w_ikas
    var deviation by TestProtocols.deviation
    var ikasResult by TestProtocols.ikasResult


    //VIU

    var u_viu by TestProtocols.u_viu
    var i_viu by TestProtocols.i_viu
    var t_viu by TestProtocols.t_viu
    var viuResult by TestProtocols.viuResult

    //MVZ HH
    var u_uv_hh by TestProtocols.u_uv_hh
    var u_vw_hh by TestProtocols.u_vw_hh
    var u_wu_hh by TestProtocols.u_wu_hh
    var i_u_hh by TestProtocols.i_u_hh
    var i_v_hh by TestProtocols.i_v_hh
    var i_w_hh by TestProtocols.i_w_hh
    var cos_hh by TestProtocols.cos_hh
    var hhResult by TestProtocols.hhResult


    //TL
    var u_set_tl by TestProtocols.u_set_tl
    var u_izm_tl by TestProtocols.u_izm_tl
    var i_u_tl by TestProtocols.i_u_tl
    var p_tl by TestProtocols.p_tl
    var induction_tl by TestProtocols.induction_tl
    var p_steel_tl by TestProtocols.p_steel_tl
    var intensity_tl by TestProtocols.intensity_tl
    var losses_tl by TestProtocols.losses_tl
    var tlResult by TestProtocols.tlResult



    override fun toString() = name

}