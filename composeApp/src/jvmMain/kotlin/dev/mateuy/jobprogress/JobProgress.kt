package dev.mateuy.jobprogress

data class JobProgress(val tasksAmmount: Int, val doneAmmount:Int = 0){
    companion object{
        val unknownProgress = JobProgress(1)
    }
    val completed get() = doneAmmount==tasksAmmount
    fun taskDone() = copy(doneAmmount = this.doneAmmount +1)
    fun complete() = copy(doneAmmount = tasksAmmount)
}

