package ru.avem.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.common.ProtocolBuilder
import ru.avem.modules.tests.CustomController
import java.sql.Connection


object DBManager {
    init {
        Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        validateData()
    }

    private fun validateData() {
        transaction {
            SchemaUtils.create(TestItems, TestProtocols, Users, SteelMarks)
        }
        if (getAllSteelMarks().isEmpty()) {
            transaction {
                SteelMarksObjects.new {
                    name = "2013"
                    density = "7850"
                    losses = "2.5"
                    intensity = "185"
                    error = "10"
                }

                SteelMarksObjects.new {
                    name = "2312"
                    density = "7750"
                    losses = "1.75"
                    intensity = "274"
                    error = "10"
                }

                SteelMarksObjects.new {
                    name = "2411"
                    density = "7650"
                    losses = "1.6"
                    intensity = "116"
                    error = "10"
                }

                SteelMarksObjects.new {
                    name = "2212"
                    density = "7800"
                    losses = "2.2"
                    intensity = "240"
                    error = "10"
                }

                SteelMarksObjects.new {
                    name = "1211"
                    density = "7800"
                    losses = "3.3"
                    intensity = "502"
                    error = "10"
                }
            }
        }
        if (getAllUsers().isEmpty()) {
            addUser("admin", "888", true)
        }
        if (getAllTI().isEmpty()) {
            addTI(
                name = "Test",
                scheme = true,
                power = "20",
                u_linear = "380",
                i = "20",
                i_viu = "0.2",
                i_mz = "60",
                u_viu = "1760",
                u_mgr = "1000",
                t_viu = "60",
                t_hh = "60",
                t_mv = "10",
                r_max = "200000",
                r_min = "500",
                r20_max = "0",
                r20_min = "0",
                steel_mark = "2013",
                intensity = false,
                d_inside =  "",
                d_outer =  "",
                stator_length = "",
                height_slot = "",
                material = false
            )
        }
    }

    fun getSteelMark(value: String) = transaction { SteelMarksObjects.find { SteelMarks.name eq value }.first() }

    fun getAllSteelMarks(): List<String> {
        return transaction {
            SteelMarksObjects.all().map { it.name }
        }
    }

    fun addSteelMark(
        name: String,
        density: String,
        losses: String,
        intensity: String,
        error: String,
    ) {
        transaction {
            SteelMarksObjects.new {
                this.name = name
                this.density = density
                this.losses = losses
                this.intensity = intensity
                this.error = error
            }
        }
    }

    fun deleteSteelMark(name: String) {
        transaction {
            SteelMarks.deleteWhere {
                SteelMarks.name eq name
            }
        }
    }
    fun getAllUsers(): List<User> {
        return transaction {
            User.all().toList()
        }
    }
    fun addUser(
        login: String,
        password: String,
        isAdmin: Boolean
    ) {

        transaction {
            User.new {
                this.login = login
                this.password = password
                this.isAdmin = isAdmin
            }
        }
    }

    fun getAllTI(): List<String> {
        return transaction {
            TestItem.all().map { it.name }
        }
    }
    fun getAllTI1(): List<TestItem> {
        return transaction {
            TestItem.all().toList()
        }
    }


    fun getAllProtocols(): List<TestProtocol> {
        return transaction {
            TestProtocol.all().reversed().toList()
        }
    }

    fun getTI(value: String) = transaction { TestItem.find { TestItems.name eq value }.first() }
    fun getProtocolById(protocol: TestProtocol) = transaction { TestProtocol.find { TestProtocols.id eq protocol.id }.first() }

    fun addTI(
        name: String,
        scheme: Boolean,
        power: String,
        u_linear: String,
        i: String,
        i_viu: String,
        i_mz: String,
        u_viu: String,
        u_mgr: String,
        t_viu: String,
        t_hh: String,
        t_mv: String,
        r_max: String,
        r_min: String,
        r20_max: String,
        r20_min: String,
        steel_mark: String,
        intensity: Boolean,
        d_inside: String,
        d_outer: String,
        stator_length: String,
        height_slot: String,
        material: Boolean
    ) {
        transaction {
            TestItem.new {
                this.name = name
                this.scheme = scheme
                this.power = power
                this.u_linear = u_linear
                this.i = i
                this.i_viu = i_viu
                this.i_mz = i_mz
                this.u_viu = u_viu
                this.u_mgr = u_mgr
                this.t_viu = t_viu
                this.t_hh = t_hh
                this.t_mv = t_mv
                this.r_max = r_max
                this.r_min = r_min
                this.r20_max = r20_max
                this.r20_min = r20_min
                this.steel_mark = steel_mark
                this.isolation = intensity
                this.d_inside = d_inside
                this.d_outer = d_outer
                this.stator_length = stator_length
                this.height_slot = height_slot
                this.material = material
            }
        }
    }

    fun deleteTestItemById(name: String) {
        transaction {
            TestItems.deleteWhere {
                TestItems.name eq name
            }
        }
    }

    fun deleteProtocolItemByName1(item: TestProtocol) {
        transaction {
            TestProtocols.deleteWhere {
                TestProtocols.id eq item.id
            }
        }
    }
    fun deleteUser(user: User) {
        transaction {
            Users.deleteWhere {
                Users.id eq user.id
            }
        }
    }

    fun updateUser(user: User, newLogin: String, newPassword: String, isAdminStatus: Boolean) {
        transaction {
            Users.update ({ Users.id eq user.id }) {
                it[login] = newLogin
                it[password] = newPassword
                it[isAdmin] = isAdminStatus
            }
        }
    }
    fun updateSteelMark(
        steelMark: SteelMarksObjects,
        newName: String,
        newDensity: String,
        newLosses: String,
        newIntensity: String,
        newError: String
    ) {
        transaction {
            SteelMarks.update ({ SteelMarks.id eq steelMark.id }) {
                it[name] = newName
                it[density] = newDensity
                it[losses] = newLosses
                it[intensity] = newIntensity
                it[error] = newError
            }
        }
    }

    fun findUser(login: String): List<User> {
        return getAllUsers().filter { it.login.contains(login) }
    }
    fun findUserLogin(login: String): Boolean {
        return getAllUsers().find { it.login == login} != null
    }
    fun findProtocol(itemName: String): List<TestProtocol> {
        return getAllProtocols().filter { it.name.contains(itemName) }
    }

    fun findCurrentProtocol(itemName: String): TestProtocol? {
        return getAllProtocols().find { it.name == itemName }
    }

    fun addNewProtocol(name: String) {
        val currentTest = ProtocolBuilder

        if (findCurrentProtocol(name) != null) {
            transaction {
                TestProtocols.update ({ TestProtocols.name eq name }) {

                    it[specifiedU] = CustomController.testObject.u_linear
                    it[specifiedI] = CustomController.testObject.i
//                    it[specifiedRPM] = CustomController.testObject.specifiedRPM
//                    it[specifiedP] = CustomController.testObject.specifiedP
                    it[specifiedIdleTime] = CustomController.testObject.t_hh
//                    it[specifiedRunningTime] = CustomController.testObject.specifiedRunningTime
                    it[specifiedMgrU] = CustomController.testObject.u_mgr
                    it[specifiedViuU] = CustomController.testObject.u_viu
                    it[operator] = currentTest.operator
                    it[date] = currentTest.date
                    it[time] = currentTest.time
                    it[scheme] = CustomController.testObject.scheme

                    //MGR
                    if (currentTest.mgrResult != "") {
                        it[mgrU] = currentTest.u
                        it[mgrR15] = currentTest.r15
                        it[mgrR60] = currentTest.r60
                        it[mgrkABS] = currentTest.kABS
                        it[mgrT] = currentTest.mgrT
                    }
                    //IKAS
                    if (currentTest.ikasResult != "") {
                        it[ikasRuv] = currentTest.r_uv_ikas
                        it[ikasRvw] = currentTest.r_vw_ikas
                        it[ikasRwu] = currentTest.r_wu_ikas
                        it[calc_u_ikas] = currentTest.calc_u_ikas
                        it[calc_v_ikas] = currentTest.calc_v_ikas
                        it[calc_w_ikas] = currentTest.calc_w_ikas
                        it[deviation] = currentTest.deviation
                    }
                    //VIU
                    if (currentTest.viuResult != "") {
                        it[u_viu] = currentTest.u_viu
                        it[i_viu] = currentTest.i_viu
                        it[t_viu] = currentTest.t_viu
                    }

                    //HH MVZ
                    if (currentTest.hhResult != "") {
                        it[u_uv_hh] = currentTest.u_uv_hh
                        it[u_vw_hh] = currentTest.u_vw_hh
                        it[u_wu_hh] = currentTest.u_wu_hh
                        it[i_u_hh] = currentTest.i_u_hh
                        it[i_v_hh] = currentTest.i_v_hh
                        it[i_w_hh] = currentTest.i_w_hh
                    }

                    //TL
                    if (currentTest.tlResult != "") {
                        it[u_set_tl] = currentTest.u_set_tl
                        it[u_izm_tl] = currentTest.u_izm_tl
                        it[i_u_tl] = currentTest.i_u_tl
                        it[p_tl] = currentTest.p_tl
                        it[induction_tl] = currentTest.induction_tl
                        it[p_steel_tl] = currentTest.p_steel_tl
                        it[intensity_tl] = currentTest.intensity_tl
                        it[losses_tl] = currentTest.losses_tl
                    }
                }
            }
        } else {
            transaction {
                TestProtocol.new {
                    this.name = name
                    this.type = CustomController.testObject.name
                    this.operator = currentTest.operator
                    this.time = currentTest.time
                    this.date = currentTest.date

                    this.specifiedU = CustomController.testObject.u_linear
                    this.specifiedI = CustomController.testObject.i
//                    it[specifiedRPM] = CustomController.testObject.specifiedRPM
//                    it[specifiedP] = CustomController.testObject.specifiedP
                    this.specifiedIdleTime = CustomController.testObject.t_hh
//                    it[specifiedRunningTime] = CustomController.testObject.specifiedRunningTime
                    this.specifiedMgrU = CustomController.testObject.u_mgr
                    this.specifiedViuU = CustomController.testObject.u_viu
                    this.operator = currentTest.operator
                    this.date = currentTest.date
                    this.time = currentTest.time
                    this.scheme = CustomController.testObject.scheme

                    this.mgrU = currentTest.u
                    this.mgrR15 = currentTest.r15
                    this.mgrR60 = currentTest.r60
                    this.mgrkABS = currentTest.kABS
                    this.mgrResult = currentTest.mgrT


                    this.ikasRuv = currentTest.r_uv_ikas
                    this.ikasRvw = currentTest.r_vw_ikas
                    this.ikasRwu = currentTest.r_wu_ikas
                    this.calc_u_ikas = currentTest.calc_u_ikas
                    this.calc_v_ikas = currentTest.calc_v_ikas
                    this.calc_w_ikas = currentTest.calc_w_ikas
                    this.deviation = currentTest.deviation
                    this.ikasResult = currentTest.ikasResult

                    this.u_viu = currentTest.u_viu
                    this.i_viu = currentTest.i_viu
                    this.t_viu = currentTest.t_viu
                    this.viuResult = currentTest.viuResult

                    //HH MVZ
                    this.u_uv_hh = currentTest.u_uv_hh
                    this.u_vw_hh = currentTest.u_vw_hh
                    this.u_wu_hh = currentTest.u_wu_hh
                    this.i_u_hh = currentTest.i_u_hh
                    this.i_v_hh = currentTest.i_v_hh
                    this.i_w_hh = currentTest.i_w_hh
                    this.hhResult = currentTest.hhResult

                    //HH
                    this.u_set_tl = currentTest.u_set_tl
                    this.u_izm_tl = currentTest.u_izm_tl
                    this.i_u_tl = currentTest.i_u_tl
                    this.p_tl = currentTest.p_tl
                    this.induction_tl = currentTest.induction_tl
                    this.p_steel_tl = currentTest.p_steel_tl
                    this.intensity_tl = currentTest.intensity_tl
                    this.losses_tl = currentTest.losses_tl
                    this.tlResult = currentTest.tlResult

                }
            }
        }


    }
}

