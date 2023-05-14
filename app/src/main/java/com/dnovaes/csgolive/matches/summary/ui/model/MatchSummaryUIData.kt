package com.dnovaes.csgolive.matches.summary.ui.model

import com.dnovaes.csgolive.matches.common.ui.model.GameMatch
import com.dnovaes.csgolive.common.ui.viewstate.UIDataState
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState

// Check methods

fun UIViewState<GameMatch>.isStartingLoadSummaryData() =
    this.state == com.dnovaes.csgolive.common.ui.viewstate.UIDataState.STARTED
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH

fun UIViewState<GameMatch>.isProcessingLoadSummaryData() =
    this.state == com.dnovaes.csgolive.common.ui.viewstate.UIDataState.PROCESSING
        && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH

fun UIViewState<GameMatch>.isDoneLoadingSummaryData() =
    this.state == com.dnovaes.csgolive.common.ui.viewstate.UIDataState.DONE
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH


// Assigning methods

fun UIViewState<GameMatch>.asProcessingSummaryData() = UIViewState<GameMatch>(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
    state = UIDataState.PROCESSING
)