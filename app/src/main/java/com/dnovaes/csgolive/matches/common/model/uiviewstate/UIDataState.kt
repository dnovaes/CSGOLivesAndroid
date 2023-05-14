package com.dnovaes.csgolive.matches.common.model.uiviewstate

import com.dnovaes.csgolive.matches.common.model.UIModelInterface

interface UIDataStateInterface

enum class UIDataState: UIDataStateInterface {
    IDLE,
    STARTED,
    PROCESSING,
    CANCELED,
    DONE
}

fun <T: UIModelInterface> UIViewState<T>.inStart(): UIViewState<T>
        = this.copy(state = UIDataState.STARTED)

fun <T: UIModelInterface> UIViewState<T>.inProcessing(): UIViewState<T>
    = this.copy(state = UIDataState.PROCESSING)

fun <T: UIModelInterface> UIViewState<T>.inDone(): UIViewState<T>
        = this.copy(state = UIDataState.DONE)

