package ru.avem.modules.tests.ikas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.components.*
import ru.avem.components.LogsList
import ru.avem.modules.tests.Test
import ru.avem.viewmodels.MainScreenViewModel
import ru.avem.data.enums.TestEnum
import ru.avem.modules.tests.CustomController.isStartButton
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.viewmodels.TestScreenViewModel


class IKASScreen(private var mainViewModel: MainScreenViewModel) : Test() {
    override val testName: String
        get() = TestEnum.nameIKAS.testName

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { TestScreenViewModel() }
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val navigator = LocalNavigator.currentOrThrow

//        LifecycleEffect(onStarted = { viewModel.clearFields() })

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.border(2.dp, Color.Black)
                    ) {
                        SpecifiedParamsList()
                        Column(
                            modifier = Modifier.fillMaxHeight(0.6f).fillMaxWidth(0.8f).border(1.dp, Color.LightGray)
                        ) {
                            IKASTestWindow(viewModel)
                            if (viewModel.warningUV.value) {
                                TestDialog(
                                    text = "Подключите провода ИКАС-А и ИКАС-N к UV",
                                    other = true,
                                    action1 = {viewModel.warningUV.value = false},
                                    action2 = {
                                        isTestRunning.value = false
                                        viewModel.warningUV.value = false
                                    }
                                )
                            }
                            if (viewModel.warningVW.value) {
                                TestDialog(
                                    text = "Подключите провода ИКАС-А и ИКАС-N к VW",
                                    other = true,
                                    action1 = {viewModel.warningVW.value = false},
                                    action2 = {
                                        isTestRunning.value = false
                                        viewModel.warningVW.value = false
                                    }
                                )
                            }
                            if (viewModel.warningWU.value) {
                                TestDialog(
                                    text = "Подключите провода ИКАС-А и ИКАС-N к WU",
                                    other = true,
                                    action1 = {viewModel.warningWU.value = false},
                                    action2 = {
                                        isTestRunning.value = false
                                        viewModel.warningWU.value = false
                                    }
                                )
                            }
                        }
                        ProtectionStatusContainer()
                    }
                    TestNavigationBar(
                        mainViewModel,
                        viewModel,
                        navigator,
                        TestEnum.nameIKAS
                    )
                    LogsList(viewModel.loggerScope)
                }
                if (isStartButton.value) {
                    TestDialog()
                }
            },
            bottomBar = { }
        )
    }

}