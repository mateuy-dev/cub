package dev.mateuy.cube.gui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.mateuy.makineta.ui.screens.FullCenteredColumn
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.mateuy.cube.ServiceLocator
import dev.mateuy.cube.gui.LocalLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        var studentNames by remember { mutableStateOf("") }
        val loadingState = LocalLoadingState.currentOrThrow
        FullCenteredColumn {
            Text("Benvingut al Cub",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("El Sistema d'Administració de l'esfera (antic SaSaga - Sistem d'administració del SAGA)",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))

            Text("A l'iniciar l'aplicació s'obrirà una finestra del Chrome; no la tanquis. Un cop oberta torna el Cub i segueix les instruccions.")

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    loadingState.loading()
                    ServiceLocator.start()
                    navigator.replace(StudentListFormScreen())
                    loadingState.endLoading()

                }

            }){
                Text(text = "Continuar")
            }

        }
    }
}
