package com.dnovaes.csgolive.matches.common.ui.model

import androidx.annotation.StringRes
import com.dnovaes.csgolive.R

data class UIError(
    @StringRes val stringRes: Int = R.string.default_ui_error_message,
    val throwable: Throwable? = null
)
