package dev.mateuy.cube.gui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.mateuy.makineta.ui.screens.FullCenteredColumn
import dev.mateuy.cube.ServiceLocator
import dev.mateuy.cube.data.UpdateError
import dev.mateuy.cube.gui.LocalLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuScreen: Screen {
    @Composable
    override fun Content() {
        var nameErrors by remember { mutableStateOf(listOf<UpdateError>()) }
        var skipErrorsState by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val coroutinesScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val loadingState = LocalLoadingState.currentOrThrow

        FullCenteredColumn {
            Text("Ves a la pàgina amb la taula que vulguis processar. Un cop fet incia que vols fer")
            /*Button(onClick = {
            }, modifier = Modifier.padding(10.dp)){
                Text("Validar Saga")
            }*/
            Button(onClick = {
                coroutinesScope.launch(Dispatchers.IO) {
                    loadingState.loading()
                    val readTable = kotlin.runCatching {
                        ServiceLocator.cubeInteractors.validateStudentNames()
                    }
                    readTable.onFailure {
                        it.printStackTrace()
                        launch { snackbarHostState.showSnackbar("Error al llegir l'Esfera. Estàs a la pantalla correcte?") }
                    }
                    readTable.onSuccess { newErrors ->
                        if(newErrors.isEmpty() || skipErrorsState)
                            navigator.replace(GradesFormScreen())
                        else
                            nameErrors = newErrors
                    }
                    loadingState.endLoading()
                }

            }, modifier = Modifier.padding(10.dp)){
                Text("Omplir notes Esfera")

            }
            if(nameErrors.isNotEmpty()){
                Row {
                    Text("Saltar errors")
                    Checkbox(skipErrorsState, onCheckedChange = {skipErrorsState = it})
                }
                ErrorCard("Alumnes introduïts no coincideixen amb els de l'Esfera"){
                    Row{
                        NamesColumn("No introduïts", nameErrors.filter { it is UpdateError.StudentNotFoundInGrades }.map { it.student })
                        NamesColumn("No a l'Esfera", nameErrors.filter { it is UpdateError.StudentNotFoundInSaga }.map { it.student })
                    }
                }
            }

            SnackbarHost(hostState = snackbarHostState)
        }
    }

    @Composable
    private fun NamesColumn(title: String, names: List<String>) {
        Column{
            Text(title, style = MaterialTheme.typography.titleSmall)
            LazyColumn {
                items(names){
                    Text(it)
                }
            }
        }



    }
}
