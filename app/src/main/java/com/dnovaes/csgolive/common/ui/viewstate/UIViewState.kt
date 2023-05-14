package com.dnovaes.csgolive.common.ui.viewstate

import com.dnovaes.csgolive.matches.common.ui.model.UIError
import com.dnovaes.csgolive.matches.common.ui.model.UIModelInterface

open class UIViewState<T: UIModelInterface>(
    val state: UIDataStateInterface,
    val process: UIDataProcessInterface,
    val result: T? = null,
    val error: UIError? = null
) {

    fun copy(
        process: UIDataProcessInterface = this.process,
        state: UIDataStateInterface = this.state,
        result: T? = this.result,
        error: UIError? = this.error
    ): UIViewState<T> {
        return UIViewState<T>(
            process = process,
            state = state,
            result = result,
            error = error
        )
    }

    open fun withResult(result: T?): UIViewState<T> = this.copy(result = result)

    open fun withError(errorParam: UIError?): UIViewState<T> = this.copy(error = errorParam)

}
