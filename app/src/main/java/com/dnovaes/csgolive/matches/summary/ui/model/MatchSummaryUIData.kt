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


fun UIViewState<Matches>.isProcessingLoadSummaryDataFromPage() =
    this.state == UIDataState.PROCESSING
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH_FROM_PAGE

fun UIViewState<Matches>.isDoneLoadingSummaryDataFromPage() =
    this.state == UIDataState.DONE
            && this.process == MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH_FROM_PAGE


// Assigning methods

fun UIViewState<Matches>.asProcessingSummaryData() = copy(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
    state = UIDataState.PROCESSING
)
fun UIViewState<Matches>.asLoadedSummaryData() = copy(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
    state = UIDataState.DONE
)

fun UIViewState<Matches>.asProcessingSummaryDataFromPage() = copy(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH_FROM_PAGE,
    state = UIDataState.PROCESSING
)
fun UIViewState<Matches>.asLoadedSummaryDataFromPage() = copy(
    process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH_FROM_PAGE,
    state = UIDataState.DONE
)
