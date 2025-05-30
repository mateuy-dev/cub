package dev.mateuy.enelrecord.ui

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import dev.mateuy.jobprogress.JobProgress
import dev.mateuy.makineta.ui.screens.FullCenteredColumn
import kotlinx.coroutines.flow.Flow


@Composable
fun JobProgressScreen(message: String, progressFlow: Flow<JobProgress>) {
    val progress by progressFlow.collectAsState(JobProgress.unknownProgress)

    FullCenteredColumn {
        CircularProgressIndicator()
        Text(message)
        Text("${progress.doneAmmount+1} de ${progress.tasksAmmount}")
    }

}
