package dev.mateuy.cube.gui



import dev.mateuy.jobprogress.JobProgress
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.receiveAsFlow

sealed class Screens<out T> {
  object MenuScreen : Screens<Nothing>()
  object StudentListFromScreen : Screens<Nothing>()
  object GradesListFromScreen : Screens<Nothing>()

  class ProgressScreen(val task: String ="", private val progressChannel: ReceiveChannel<JobProgress>) : Screens<Nothing>(){
    val progressFlow get() = progressChannel.receiveAsFlow()
  }

}
