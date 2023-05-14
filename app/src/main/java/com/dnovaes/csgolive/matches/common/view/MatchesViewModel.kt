package com.dnovaes.csgolive.matches.common.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnovaes.csgolive.matches.common.model.uiviewstate.UIViewState
import com.dnovaes.csgolive.matches.common.model.GameMatch
import com.dnovaes.csgolive.matches.common.model.uiviewstate.UIDataState
import com.dnovaes.csgolive.matches.summary.model.MatchSummaryUIDataProcess
import com.dnovaes.csgolive.matches.summary.model.asProcessingSummaryData

class MatchesViewModel: ViewModel() {

    private var matchState = UIViewState<GameMatch>(
        process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
        state = UIDataState.STARTED
    )

    private val _matchesLiveData: MutableLiveData<UIViewState<GameMatch>> = MutableLiveData(matchState)
    val matchesLiveData: LiveData<UIViewState<GameMatch>> = _matchesLiveData

    fun loadSummaryData() {
        matchState = matchState
            .asProcessingSummaryData()
            .withError(null)
        _matchesLiveData.postValue(matchState)
    }
}