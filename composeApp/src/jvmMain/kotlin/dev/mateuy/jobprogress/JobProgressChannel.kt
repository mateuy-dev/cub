package dev.mateuy.jobprogress


import dev.mateuy.cube.logger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

interface IJobProgressChannel{
    suspend fun sendTasksCount(tasksCount: Int)
    suspend fun sendTaskDone()
    suspend fun sendTaskFinished()
}

data class JobProgressChannel(private val channel : Channel<JobProgress> = Channel(), private var current : JobProgress = JobProgress(1)): ReceiveChannel<JobProgress> by channel, IJobProgressChannel{
    override suspend fun sendTasksCount(tasksCount: Int){
        current = JobProgress(tasksCount)
        channel.send(current)
    }

    override suspend fun sendTaskDone() {
        current = current.taskDone()
        channel.send(current)
        if(current.completed)
            channel.close()
    }
    override suspend fun sendTaskFinished(){
        if(!channel.isClosedForSend) {
            current.complete()
            channel.send(current)
            channel.close()
        }
    }
}

class NopJobProgressChannel : IJobProgressChannel{
    override suspend fun sendTasksCount(tasksCount: Int) {}
    override suspend fun sendTaskDone() {}
    override suspend fun sendTaskFinished() {}
}

class LogJobProgress(private var current : JobProgress = JobProgress(1)) : IJobProgressChannel{
    override suspend fun sendTasksCount(tasksCount: Int) {
        current = JobProgress(tasksCount)
        logger.debug{"Job progress: $current"}
    }
    override suspend fun sendTaskDone() {
        current = current.taskDone()
        logger.debug{"Job progress: $current"}
    }
    override suspend fun sendTaskFinished() {}
}