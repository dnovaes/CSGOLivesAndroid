package com.dnovaes.csgolive.matches.common.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.matches.common.ui.model.GameMatch
import com.dnovaes.csgolive.common.ui.viewstate.UIDataState
import com.dnovaes.csgolive.matches.summary.data.MatchesRepository
import com.dnovaes.csgolive.matches.summary.ui.model.MatchSummaryUIDataProcess
import com.dnovaes.csgolive.matches.summary.ui.model.asProcessingSummaryData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
): ViewModel() {

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
        viewModelScope.launch {
/*
            matchesRepository.requestMatchesList().collect { result ->
                //handleMatchesResponse(result as Result<GameMatches>)
            }
*/
        }
    }
}