package dev.mateuy.cube.gui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateuy.makineta.ui.screens.FullCenteredColumn
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.mateuy.cube.ServiceLocator
import dev.mateuy.cube.data.CubeError
import dev.mateuy.cube.gui.LocalLoadingState
import dev.mateuy.cube.nullIfEmpty
import dev.mateuy.cube.simpleChars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GradesFormScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        var studentgrades by remember { mutableStateOf("") }
        val loadingState = LocalLoadingState.currentOrThrow
        var validateAverage = false //by remember { mutableStateOf(true) }
        var errors by remember { mutableStateOf(listOf<CubeError>()) }
        val grades = studentgrades.lines().map { it.split("\t").map { it.nullIfEmpty() } }

        FullCenteredColumn {
            if (errors.isNotEmpty()) {
                ErrorsDisplay(errors)
            }
            Button(onClick = {
                navigator.replace(MenuScreen())
            }, modifier = Modifier.padding(5.dp)) {
                Text(text = "Seleccionar una nova operació")
            }
            Spacer(modifier = Modifier.height(10.dp))
//            Row {
//                Text("Validar nota mòdul (valida que l'última nota sigui una nota de mòdul correcte)")
//                Checkbox(validateAverage, onCheckedChange = { validateAverage = it })
//            }
            Button(onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    loadingState.loading()
                    errors = ServiceLocator.cubeInteractors.applyGrades(grades, validateAverage)
                    loadingState.endLoading()
                }
            }, modifier = Modifier.padding(5.dp)) {
                Text(text = "Introduïr")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = studentgrades,
                    onValueChange = { studentgrades = it },
                    modifier = Modifier.height(500.dp),
                    label = { Text("Notes estudiants del RA") }
                )
                val studentWithGradesResult =
                    kotlin.runCatching { ServiceLocator.cubeInteractors.mixGradesWithStudents(grades) }
                studentWithGradesResult.onSuccess { studentWithGrades ->
                    Column {
                        ServiceLocator.cubeInteractors.students.forEach {
                            Row {
                                Text(it, modifier = Modifier.width(250.dp))
                                studentWithGrades[it.simpleChars()]?.grades?.forEach {
                                    Text(it ?: " ", Modifier.width(30.dp))
                                }
                            }
                        }
                    }
                }

            }
        }
    }


}
