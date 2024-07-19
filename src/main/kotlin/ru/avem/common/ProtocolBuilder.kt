package ru.avem.common

import ru.avem.db.DBManager
import ru.avem.db.TestProtocol


object ProtocolBuilder {

    var id = ""
    var name =  ""
    var type =  ""
    var operator = ""
    var time = ""
    var date = ""

    var specifiedP = ""
    var specifiedU = ""
    var specifiedI = ""
    var specifiedRPM = ""
    var specifiedIdleTime = ""
    var specifiedRunningTime = ""
    var specifiedMgrU = ""
    var specifiedViuU = ""
    var scheme = ""

    //MGR
    var mgrTestName = ""
    var u_1 = ""
    var r15_1 = ""
    var r60_1 = ""
    var kABS_1 = ""
    var mgrT_1 = ""
    var mgrResult_1 = ""
    var u_2 = ""
    var r15_2 = ""
    var r60_2 = ""
    var kABS_2 = ""
    var mgrT_2 = ""
    var mgrResult_2 = ""
    var u_3 = ""
    var r15_3 = ""
    var r60_3 = ""
    var kABS_3 = ""
    var mgrT_3 = ""
    var mgrResult_3 = ""

    //VIU
    var viuTestName = ""
    var viuU_1 = ""
    var viuI_1 = ""
    var viuTime_1 = ""
    var viuResult_1 = ""

    var viuU_2 = ""
    var viuI_2 = ""
    var viuTime_2 = ""
    var viuResult_2 = ""

    var viuU_3 = ""
    var viuI_3 = ""
    var viuTime_3 = ""
    var viuResult_3 = ""

    //IKAS
    var ikasTestName = ""
    var r_uv_1 = ""
    var r_vw_1 = ""
    var r_wu_1 = ""
    var ikasResult_1 = ""

    var r_uv_2 = ""
    var r_vw_2 = ""
    var r_wu_2 = ""
    var ikasResult_2 = ""

    var r_uv_3 = ""
    var r_vw_3 = ""
    var r_wu_3 = ""
    var ikasResult_3 = ""

    //MV
    var mvTestName = ""
    var before_i_u_hh_1 = ""
    var before_i_v_hh_1 = ""
    var before_i_w_hh_1 = ""
    var before_i_u_hh_2 = ""
    var before_i_v_hh_2 = ""
    var before_i_w_hh_2 = ""
    var before_i_u_hh_3 = ""
    var before_i_v_hh_3 = ""
    var before_i_w_hh_3 = ""
    var after_i_u_hh_1 = ""
    var after_i_v_hh_1 = ""
    var after_i_w_hh_1 = ""
    var after_i_u_hh_2 = ""
    var after_i_v_hh_2 = ""
    var after_i_w_hh_2 = ""
    var after_i_u_hh_3 = ""
    var after_i_v_hh_3 = ""
    var after_i_w_hh_3 = ""
    var before_u_uv_mv_1 = ""
    var before_u_vw_mv_1 = ""
    var before_u_wu_mv_1 = ""
    var before_u_uv_mv_2 = ""
    var before_u_vw_mv_2 = ""
    var before_u_wu_mv_2 = ""
    var before_u_uv_mv_3 = ""
    var before_u_vw_mv_3 = ""
    var before_u_wu_mv_3 = ""
    var after_u_uv_mv_1 = ""
    var after_u_vw_mv_1 = ""
    var after_u_wu_mv_1 = ""
    var after_u_uv_mv_2 = ""
    var after_u_vw_mv_2 = ""
    var after_u_wu_mv_2 = ""
    var after_u_uv_mv_3 = ""
    var after_u_vw_mv_3 = ""
    var after_u_wu_mv_3 = ""
    var result_a_mv_1 = ""
    var result_b_mv_1 = ""
    var result_c_mv_1 = ""
    var result_a_mv_2 = ""
    var result_b_mv_2 = ""
    var result_c_mv_2 = ""
    var result_a_mv_3 = ""
    var result_b_mv_3 = ""
    var result_c_mv_3 = ""


    //HH//
    var hhTestName = ""
    var u_uv_hh_1 = ""
    var u_vw_hh_1 = ""
    var u_wu_hh_1 = ""
    var i_u_hh_1 = ""
    var i_v_hh_1 = ""
    var i_w_hh_1 = ""
    var u_uv_hh_2 = ""
    var u_vw_hh_2 = ""
    var u_wu_hh_2 = ""
    var i_u_hh_2 = ""
    var i_v_hh_2 = ""
    var i_w_hh_2 = ""
    var u_uv_hh_3 = ""
    var u_vw_hh_3 = ""
    var u_wu_hh_3 = ""
    var i_u_hh_3 = ""
    var i_v_hh_3 = ""
    var i_w_hh_3 = ""


    fun clear() {
        id = ""
        name =  ""
        type =  ""
        operator = ""
        time = ""
        date = ""

        specifiedP = ""
        specifiedU = ""
        specifiedI = ""
        specifiedRPM = ""
        specifiedIdleTime = ""
        specifiedRunningTime = ""
        specifiedMgrU = ""
        specifiedViuU = ""
        scheme = ""

        //MGR
        mgrTestName = ""
        u_1 = ""
        r15_1 = ""
        r60_1 = ""
        kABS_1 = ""
        mgrT_1 = ""
        mgrResult_1 = ""
        u_2 = ""
        r15_2 = ""
        r60_2 = ""
        kABS_2 = ""
        mgrT_2 = ""
        mgrResult_2 = ""
        u_3 = ""
        r15_3 = ""
        r60_3 = ""
        kABS_3 = ""
        mgrT_3 = ""
        mgrResult_3 = ""

        //IKAS
        ikasTestName = ""

        r_uv_1 = ""
        r_vw_1 = ""
        r_wu_1 = ""
        ikasResult_1 = ""

        r_uv_2 = ""
        r_vw_2 = ""
        r_wu_2 = ""
        ikasResult_2 = ""

        r_uv_3 = ""
        r_vw_3 = ""
        r_wu_3 = ""
        ikasResult_3 = ""

        //VIU
        viuTestName = ""
        viuU_1 = ""
        viuI_1 = ""
        viuTime_1 = ""
        viuResult_1 = ""
        viuU_2 = ""
        viuI_2 = ""
        viuTime_2 = ""
        viuResult_2 = ""
        viuU_3 = ""
        viuI_3 = ""
        viuTime_3 = ""
        viuResult_3 = ""

        //MV
        mvTestName = ""
        before_i_u_hh_1 = ""
        before_i_v_hh_1 = ""
        before_i_w_hh_1 = ""
        before_i_u_hh_2 = ""
        before_i_v_hh_2 = ""
        before_i_w_hh_2 = ""
        before_i_u_hh_3 = ""
        before_i_v_hh_3 = ""
        before_i_w_hh_3 = ""
        after_i_u_hh_1 = ""
        after_i_v_hh_1 = ""
        after_i_w_hh_1 = ""
        after_i_u_hh_2 = ""
        after_i_v_hh_2 = ""
        after_i_w_hh_2 = ""
        after_i_u_hh_3 = ""
        after_i_v_hh_3 = ""
        after_i_w_hh_3 = ""
        before_u_uv_mv_1 = ""
        before_u_vw_mv_1 = ""
        before_u_wu_mv_1 = ""
        before_u_uv_mv_2 = ""
        before_u_vw_mv_2 = ""
        before_u_wu_mv_2 = ""
        before_u_uv_mv_3 = ""
        before_u_vw_mv_3 = ""
        before_u_wu_mv_3 = ""
        after_u_uv_mv_1 = ""
        after_u_vw_mv_1 = ""
        after_u_wu_mv_1 = ""
        after_u_uv_mv_2 = ""
        after_u_vw_mv_2 = ""
        after_u_wu_mv_2 = ""
        after_u_uv_mv_3 = ""
        after_u_vw_mv_3 = ""
        after_u_wu_mv_3 = ""
        result_a_mv_1 = ""
        result_b_mv_1 = ""
        result_c_mv_1 = ""
        result_a_mv_2 = ""
        result_b_mv_2 = ""
        result_c_mv_2 = ""
        result_a_mv_3 = ""
        result_b_mv_3 = ""
        result_c_mv_3 = ""


        //hh
        hhTestName = ""
        u_uv_hh_1 = ""
        u_vw_hh_1 = ""
        u_wu_hh_1 = ""
        i_u_hh_1 = ""
        i_v_hh_1 = ""
        i_w_hh_1 = ""
        u_uv_hh_2 = ""
        u_vw_hh_2 = ""
        u_wu_hh_2 = ""
        i_u_hh_2 = ""
        i_v_hh_2 = ""
        i_w_hh_2 = ""
        u_uv_hh_3 = ""
        u_vw_hh_3 = ""
        u_wu_hh_3 = ""
        i_u_hh_3 = ""
        i_v_hh_3 = ""
        i_w_hh_3 = ""
    }

    fun fillProtocol (protocol: TestProtocol) {

        val t = DBManager.getProtocolById(protocol)

        id = t.id.toString()
        name =  t.name
        type =  t.type.toString()
        operator = t.operator.toString()
        time = t.time.toString()
        date = t.date.toString()

        specifiedP = t.specifiedP.toString()
        specifiedU = t.specifiedU.toString()
        specifiedI = t.specifiedI.toString()
        specifiedRPM = t.specifiedRPM.toString()
        specifiedIdleTime = t.specifiedIdleTime.toString()
        specifiedRunningTime = t.specifiedRunningTime.toString()
        specifiedMgrU = t.specifiedMgrU.toString()
        specifiedViuU = t.specifiedViuU.toString()
        scheme = t.scheme.toString()

        //MGR
        u_1 = t.mgrU_1.toString()
        r15_1 = t.mgrR15_1.toString()
        r60_1 = t.mgrR60_1.toString()
        kABS_1 = t.mgrkABS_1.toString()
        mgrT_1 = t.mgrT_1.toString()
        mgrResult_1 = t.mgrResult_1.toString()

        u_2 = t.mgrU_2.toString()
        r15_2 = t.mgrR15_2.toString()
        r60_2 = t.mgrR60_2.toString()
        kABS_2 = t.mgrkABS_2.toString()
        mgrT_2 = t.mgrT_2.toString()
        mgrResult_2 = t.mgrResult_2.toString()

        u_3 = t.mgrU_3.toString()
        r15_3 = t.mgrR15_3.toString()
        r60_3 = t.mgrR60_3.toString()
        kABS_3 = t.mgrkABS_3.toString()
        mgrT_3 = t.mgrT_3.toString()
        mgrResult_3 = t.mgrResult_3.toString()

        //VIU
        viuU_1 = t.viuU_1.toString()
        viuI_1 = t.viuI_1.toString()
        viuTime_1 = t.viuTime_1.toString()
        viuResult_1 = t.viuResult_1.toString()
        viuU_2 = t.viuU_2.toString()
        viuI_2 = t.viuI_2.toString()
        viuTime_2 = t.viuTime_2.toString()
        viuResult_2 = t.viuResult_2.toString()
        viuU_3 = t.viuU_3.toString()
        viuI_3 = t.viuI_3.toString()
        viuTime_3 = t.viuTime_3.toString()
        viuResult_3 = t.viuResult_3.toString()

         //IKAS

        r_uv_1 = t.ikasRuv1.toString()
        r_vw_1 = t.ikasRvw1.toString()
        r_wu_1 = t.ikasRwu1.toString()
        ikasResult_1 = t.ikasResult_1.toString()
        r_uv_2 = t.ikasRuv2.toString()
        r_vw_2 = t. ikasRvw2.toString()
        r_wu_2 = t. ikasRwu2.toString()
        ikasResult_2 = t.ikasResult_2.toString()
        r_uv_3 = t.ikasRuv3.toString()
        r_vw_3 = t.ikasRvw3.toString()
        r_wu_3 = t.ikasRwu3.toString()
        ikasResult_3 = t.ikasResult_3.toString()

        //MV
        before_i_u_hh_1 = t.before_i_u_hh_1.toString()
        before_i_v_hh_1 = t.before_i_v_hh_1.toString()
        before_i_w_hh_1 = t.before_i_w_hh_1.toString()
        before_i_u_hh_2 = t.before_i_u_hh_2.toString()
        before_i_v_hh_2 = t.before_i_v_hh_2.toString()
        before_i_w_hh_2 = t.before_i_w_hh_2.toString()
        before_i_u_hh_3 = t.before_i_u_hh_3.toString()
        before_i_v_hh_3 = t.before_i_v_hh_3.toString()
        before_i_w_hh_3 = t.before_i_w_hh_3.toString()
        after_i_u_hh_1 = t.after_i_u_hh_1.toString()
        after_i_v_hh_1 = t.after_i_v_hh_1.toString()
        after_i_w_hh_1 = t.after_i_w_hh_1.toString()
        after_i_u_hh_2 = t.after_i_u_hh_2.toString()
        after_i_v_hh_2 = t.after_i_v_hh_2.toString()
        after_i_w_hh_2 = t.after_i_w_hh_2.toString()
        after_i_u_hh_3 = t.after_i_u_hh_3.toString()
        after_i_v_hh_3 = t.after_i_v_hh_3.toString()
        after_i_w_hh_3 = t.after_i_w_hh_3.toString()
        before_u_uv_mv_1 = t.before_u_uv_mv_1.toString()
        before_u_vw_mv_1 = t.before_u_vw_mv_1.toString()
        before_u_wu_mv_1 = t.before_u_wu_mv_1.toString()
        before_u_uv_mv_2 = t.before_u_uv_mv_2.toString()
        before_u_vw_mv_2 = t.before_u_vw_mv_2.toString()
        before_u_wu_mv_2 = t.before_u_wu_mv_2.toString()
        before_u_uv_mv_3 = t.before_u_uv_mv_3.toString()
        before_u_vw_mv_3 = t.before_u_vw_mv_3.toString()
        before_u_wu_mv_3 = t.before_u_wu_mv_3.toString()
        after_u_uv_mv_1 = t.after_u_uv_mv_1.toString()
        after_u_vw_mv_1 = t.after_u_vw_mv_1.toString()
        after_u_wu_mv_1 = t.after_u_wu_mv_1.toString()
        after_u_uv_mv_2 = t.after_u_uv_mv_2.toString()
        after_u_vw_mv_2 = t.after_u_vw_mv_2.toString()
        after_u_wu_mv_2 = t.after_u_wu_mv_2.toString()
        after_u_uv_mv_3 = t.after_u_uv_mv_3.toString()
        after_u_vw_mv_3 = t.after_u_vw_mv_3.toString()
        after_u_wu_mv_3 = t.after_u_wu_mv_3.toString()
        result_a_mv_1 = t.result_a_mv_1.toString()
        result_b_mv_1 = t.result_b_mv_1.toString()
        result_c_mv_1 = t.result_c_mv_1.toString()
        result_a_mv_2 = t.result_a_mv_2.toString()
        result_b_mv_2 = t.result_b_mv_2.toString()
        result_c_mv_2 = t.result_c_mv_2.toString()
        result_a_mv_3 = t.result_a_mv_3.toString()
        result_b_mv_3 = t.result_b_mv_3.toString()
        result_c_mv_3 = t.result_c_mv_3.toString()


        //HH
        u_uv_hh_1 = t.u_uv_hh_1.toString()
        u_vw_hh_1 = t.u_vw_hh_1.toString()
        u_wu_hh_1 = t.u_wu_hh_1.toString()
        i_u_hh_1 = t.i_u_hh_1.toString()
        i_v_hh_1 = t.i_v_hh_1.toString()
        i_w_hh_1 = t.i_w_hh_1.toString()
        u_uv_hh_2 = t.u_uv_hh_2.toString()
        u_vw_hh_2 = t.u_vw_hh_2.toString()
        u_wu_hh_2 = t.u_wu_hh_2.toString()
        i_u_hh_2 = t.i_u_hh_2.toString()
        i_v_hh_2 = t.i_v_hh_2.toString()
        i_w_hh_2 = t.i_w_hh_2.toString()
        u_uv_hh_3 = t.u_uv_hh_3.toString()
        u_vw_hh_3 = t.u_vw_hh_3.toString()
        u_wu_hh_3 = t.u_wu_hh_3.toString()
        i_u_hh_3 = t.i_u_hh_3.toString()
        i_v_hh_3 = t.i_v_hh_3.toString()
        i_w_hh_3 = t.i_w_hh_3.toString()


    }

}
