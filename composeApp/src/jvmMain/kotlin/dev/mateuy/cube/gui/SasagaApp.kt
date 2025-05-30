package dev.mateuy.cube.gui

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator

import dev.mateuy.cube.gui.screens.WelcomeScreen


@Composable
fun App() {
    var displayLoadingIndicator = remember{mutableStateOf(false)}
    MaterialTheme (){
        Box(){
            CompositionLocalProvider(LocalLoadingState providesDefault LoadingState(displayLoadingIndicator)) {
                Box(modifier = Modifier.padding(20.dp)){
                    Navigator(WelcomeScreen())
                }
            }
            if(displayLoadingIndicator.value) {
                Box(Modifier.fillMaxSize().background(Color(1f,1f,1f,0.7f))){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}


//fun main() = application {
//    val windowState = rememberWindowState(width = 1400.dp, height = 800.dp)
//    Window(onCloseRequest = ::exitApplication, title = "Sasaga", state = windowState) {
//        App()
//        /*Thread.currentThread().setUncaughtExceptionHandler{_, e ->
//            logger.error(e){"Uncaught exception"}
//        }*/
//    }
//}
