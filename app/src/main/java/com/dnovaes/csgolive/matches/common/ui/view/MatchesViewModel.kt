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
import com.dnovaes.csgolive.matches.summary.ui.model.asLoadedSummaryDataFromPage
import com.dnovaes.csgolive.matches.summary.ui.model.asProcessingSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.asProcessingSummaryDataFromPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
): ViewModel() {

    private var matchState = UIViewState<Matches>(
        process = MatchSummaryUIDataProcess.LOAD_SUMMARY_MATCH,
        state = UIDataState.STARTED,
        result = Matches(data = emptyList())
    )

    private val _matchesLiveData: MutableLiveData<UIViewState<Matches>> = MutableLiveData(matchState)
    val matchesLiveData: LiveData<UIViewState<Matches>> = _matchesLiveData

    fun refreshSummaryMatches() {
        loadInitialSummaryData()
    }

    fun loadInitialSummaryData() {
        matchState = matchState
            .asProcessingSummaryData()
            .withError(null)
        _matchesLiveData.postValue(matchState)
        viewModelScope.launch (Dispatchers.IO) {
            matchesRepository.requestMatchesList().collect { response ->
                handleInitialMatchesResponse(response)
            }
        }
    }

    private fun handleInitialMatchesResponse(response: Result<List<MatchResponse>>) {
        response.getOrNull()?.let { matchesResponses ->
            val matches = Matches(
                amountPagesLoaded = 1,
                data = matchesResponses
            )
            matchState = matchState
                .asLoadedSummaryData()
                .withResult(matches)
            _matchesLiveData.postValue(matchState)
        }
    }

    fun loadsMoreSummaryMatchesFromPage() {
        matchState.result?.let { modelState ->
            matchState = matchState
                .asProcessingSummaryDataFromPage()
                .withError(null)
            _matchesLiveData.postValue(matchState)

            val pageRequest = modelState.amountPagesLoaded + 1
            viewModelScope.launch(Dispatchers.IO) {
                matchesRepository.requestMatchesList(pageRequest).collect { response ->
                    handleMatchesFromPageResponse(pageRequest, response)
                }
            }
        }
    }


    private fun handleMatchesFromPageResponse(
        requestedPage: Int,
        response: Result<List<MatchResponse>>
    ) {
        response.getOrNull()?.let { matchesResponses ->
            matchState.result?.let { modelState ->
                val currentMatches = modelState.data.toMutableList()
                currentMatches.addAll(matchesResponses)

                val newModel = modelState.copy(
                    amountPagesLoaded = requestedPage,
                    data = currentMatches
                )
                matchState = matchState
                    .asLoadedSummaryDataFromPage()
                    .withResult(newModel)
                _matchesLiveData.postValue(matchState)
            } ?: run {
                //postError: System loading error
            }
        }
    }
}
