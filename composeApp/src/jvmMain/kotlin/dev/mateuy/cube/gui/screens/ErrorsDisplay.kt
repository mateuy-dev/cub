package dev.mateuy.cube.gui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateuy.cube.data.CubeError
import dev.mateuy.cube.ui.asMessageWithoutName

@Composable
fun ErrorsDisplay(errors: List<CubeError>) {
    ErrorCard() {
        errors.groupBy { it.student }.forEach { (student, studentErrors) ->
            Row {
                Text(student)
                Column{
                    studentErrors.forEach {
                        Text(it.asMessageWithoutName())
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorCard(title: String = "Errors", content: @Composable ()->Unit){
    Card(modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.onErrorContainer)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.error)
            content()
        }
    }
}