package dev.mateuy.cube.gui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf

class LoadingState(private val state : MutableState<Boolean>){
    var value : Boolean get() = state.value
        set(newValue) {state.value = newValue}

    fun loading() { value = true}
    fun endLoading() { value = false}
}

val LocalLoadingState = compositionLocalOf<LoadingState?> { null }

//val LocalLoadingState: ProvidableCompositionLocal<LoadingState?> =
//    staticCompositionLocalOf { null }
//
////val LocalSnackBarState: ProvidableCompositionLocal<MutableState<Boolean>?> =
////    staticCompositionLocalOf { null }