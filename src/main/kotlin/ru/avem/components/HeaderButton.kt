package ru.avem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun HeaderButton (text: String, screen: Screen) {
    val navigator = LocalNavigator.currentOrThrow

    OutlinedButton(
        onClick = {
            navigator.replace(screen)
        },
        modifier = Modifier
            .border(2.dp, if(navigator.lastItem.key == screen.key) Color.Black else Color.White)
            .width(260.dp),
        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            color = Color.Black,
            fontWeight = W700
        )
    }
}