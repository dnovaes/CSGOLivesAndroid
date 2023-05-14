package com.dnovaes.csgolive.matches.summary.model

import com.dnovaes.csgolive.matches.common.model.GameMatch
import com.dnovaes.csgolive.matches.common.model.uiviewstate.UIDataState
import com.dnovaes.csgolive.matches.common.model.uiviewstate.UIViewState

// Check methods

fun UIViewState<GameMatch>.isStartingLoadSummaryData() =
    this.state == UIDataState.STARTED
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH

fun UIViewState<GameMatch>.isProcessingLoadSummaryData() =
    this.state == UIDataState.PROCESSING
        && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH

fun UIViewState<GameMatch>.isDoneLoadingSummaryData() =
    this.state == UIDataState.DONE
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH


// Assigning methods

fun UIViewState<GameMatch>.asProcessingSummaryData() = UIViewState<GameMatch>(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
    state = UIDataState.PROCESSING
)