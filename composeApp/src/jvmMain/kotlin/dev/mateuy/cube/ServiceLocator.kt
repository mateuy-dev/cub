package dev.mateuy.cube

object ServiceLocator {
    lateinit var cubeInteractors: CubeInteractors

    fun start(){
        cubeInteractors = CubeInteractors()
    }
}