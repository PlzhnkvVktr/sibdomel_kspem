package ru.avem.modules.tests.tl

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.avem.components.*
import ru.avem.modules.tests.Test
import ru.avem.components.LogsList
import ru.avem.viewmodels.MainScreenViewModel
import ru.avem.components.SpecifiedParamsList
import ru.avem.data.enums.TestEnum
import ru.avem.modules.tests.CustomController.isStartButton
import ru.avem.modules.tests.CustomController.isTestRunning
import ru.avem.viewmodels.TestScreenViewModel


class TLScreen(private var mainViewModel: MainScreenViewModel) : Test() {
    override val testName: String
        get() = TestEnum.nameVIU.testName

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { TestScreenViewModel() }
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val navigator = LocalNavigator.currentOrThrow

        LifecycleEffect(onStarted = {
//            viewModel.clearFields()
        })

        Column {
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
                                TLTestWindow(viewModel)
                            }
                            ProtectionStatusContainer()
                        }
                        TestNavigationBar(
                            mainViewModel,
                            viewModel,
                            navigator,
                            TestEnum.nameTL
                        )
                        LogsList(viewModel.loggerScope)
                    }
                    if (viewModel.isDialog.value) {
                        TestDialog(
                            "Через расточку статора протянуть индукторные катушки Wнам и Wизм." +
                                    "Соединить разъемы соответствующих катушек." +
                                    "В случае ненадёжного соединения разъёмов оценка состояния сердечника не будет проведена.",
                            true,
                            { viewModel.isDialog.value = false },
                            {
                                isTestRunning.value = false
                                viewModel.isDialog.value = false
                            }
                        )
                    }
                    if (isStartButton.value) {
                        TestDialog()
                    }
                },
                bottomBar = { }
            )
        }
    }
}
