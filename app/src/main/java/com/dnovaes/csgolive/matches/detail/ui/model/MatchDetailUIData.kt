package com.dnovaes.csgolive.matches.detail.ui.model

import com.dnovaes.csgolive.common.ui.viewstate.UIDataState
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.matches.common.data.model.MatchDetail

// Check methods

fun UIViewState<MatchDetail>.isStartLoadMatchDetail() =
    this.state == UIDataState.STARTED
            && this.process == MatchDetailUIProcess.LOAD_MATCH_DETAIL

fun UIViewState<MatchDetail>.isProcessingLoadMatchDetail() =
    this.state == UIDataState.PROCESSING
            && this.process == MatchDetailUIProcess.LOAD_MATCH_DETAIL

fun UIViewState<MatchDetail>.isDoneLoadingMatchDetail() =
    this.state == UIDataState.DONE
            && this.process == MatchDetailUIProcess.LOAD_MATCH_DETAIL


// Assigning methods

//MATCH_DETAIL
fun UIViewState<MatchDetail>.asProcessingMatchDetail() = copy(
    process = MatchDetailUIProcess.LOAD_MATCH_DETAIL,
    state = UIDataState.PROCESSING
)
fun UIViewState<MatchDetail>.asLoadedMatchDetail() = copy(
    process = MatchDetailUIProcess.LOAD_MATCH_DETAIL,
    state = UIDataState.DONE
)

fun UIViewState<MatchDetail>.asResetLoadMatchDetail() = copy(
    process = MatchDetailUIProcess.LOAD_MATCH_DETAIL,
    state = UIDataState.IDLE
)
