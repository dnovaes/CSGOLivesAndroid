package com.dnovaes.csgolive.matches.common.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.common.ui.viewstate.UIDataState
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import com.dnovaes.csgolive.matches.common.ui.model.Matches
import com.dnovaes.csgolive.matches.summary.data.MatchesRepository
import com.dnovaes.csgolive.matches.summary.ui.model.MatchSummaryUIDataProcess
import com.dnovaes.csgolive.matches.summary.ui.model.asLoadedSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.asProcessingSummaryData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
): ViewModel() {

    private var matchState = UIViewState<Matches>(
        process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
        state = UIDataState.STARTED
    )

    private val _matchesLiveData: MutableLiveData<UIViewState<Matches>> = MutableLiveData(matchState)
    val matchesLiveData: LiveData<UIViewState<Matches>> = _matchesLiveData

    fun loadSummaryData() {
        matchState = matchState
            .asProcessingSummaryData()
            .withError(null)
        _matchesLiveData.postValue(matchState)
        viewModelScope.launch {
            matchesRepository.requestMatchesList().collect { response ->
                handleMatchesResponse(response)
            }
        }
    }

    private fun handleMatchesResponse(response: Result<List<MatchResponse>>) {
        response.getOrNull()?.let { matchesResponses ->
            val matches = Matches(matchesResponses)
            matchState = matchState
                .asLoadedSummaryData()
                .withResult(matches)
            _matchesLiveData.postValue(matchState)
        }
    }
}