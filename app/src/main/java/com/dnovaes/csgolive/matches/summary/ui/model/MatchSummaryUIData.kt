package com.dnovaes.csgolive.matches.summary.ui.model

import com.dnovaes.csgolive.matches.common.ui.model.Matches
import com.dnovaes.csgolive.common.ui.viewstate.UIDataState
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState

// Check methods

fun UIViewState<Matches>.isStartingLoadSummaryData() =
    this.state == UIDataState.STARTED
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH

fun UIViewState<Matches>.isProcessingLoadSummaryData() =
    this.state == UIDataState.PROCESSING
        && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH

fun UIViewState<Matches>.isDoneLoadingSummaryData() =
    this.state == UIDataState.DONE
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH


// Assigning methods

fun UIViewState<Matches>.asProcessingSummaryData() = UIViewState<Matches>(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
    state = UIDataState.PROCESSING
)
fun UIViewState<Matches>.asLoadedSummaryData() = UIViewState<Matches>(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
    state = UIDataState.DONE
)
