package dev.mateuy.cube.gui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.mateuy.makineta.ui.screens.FullCenteredColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.mateuy.cube.ServiceLocator
import dev.mateuy.cube.gui.LocalLoadingState

class StudentListFormScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val loadingState = LocalLoadingState.currentOrThrow
        val navigator = LocalNavigator.currentOrThrow
        var studentNames by remember { mutableStateOf("") }
        FullCenteredColumn {
            Text("Introdueix els noms dels estudiants fent copy&paste de la columna del excel",
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center)
            Button(modifier = Modifier.padding(5.dp), onClick = {
                ServiceLocator.cubeInteractors.updateStundentList(studentNames.lines())
                navigator.replace(MenuScreen())
            }){
                Text(text = "Continuar")
            }
            OutlinedTextField(
                value = studentNames,
                onValueChange = { studentNames = it},
                modifier = Modifier.height(1000.dp).width(500.dp),
                label = { Text("Noms estudiants")}
            )
        }
    }

    private fun onContinue(studentNames: String) {

    }

}
