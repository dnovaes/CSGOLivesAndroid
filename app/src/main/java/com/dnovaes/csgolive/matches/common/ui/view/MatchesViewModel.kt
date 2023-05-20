package com.dnovaes.csgolive.matches.common.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.common.ui.viewstate.UIDataState
import com.dnovaes.csgolive.matches.common.data.model.MatchDetail
import com.dnovaes.csgolive.matches.common.data.model.MatchPlayerResponse
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import com.dnovaes.csgolive.matches.common.ui.model.Matches
import com.dnovaes.csgolive.matches.detail.ui.model.MatchDetailUIProcess
import com.dnovaes.csgolive.matches.summary.data.MatchesRepository
import com.dnovaes.csgolive.matches.summary.ui.model.MatchSummaryUIDataProcess
import com.dnovaes.csgolive.matches.detail.ui.model.asLoadedMatchDetail
import com.dnovaes.csgolive.matches.summary.ui.model.asLoadedSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.asLoadedSummaryDataFromPage
import com.dnovaes.csgolive.matches.detail.ui.model.asProcessingMatchDetail
import com.dnovaes.csgolive.matches.detail.ui.model.asResetLoadMatchDetail
import com.dnovaes.csgolive.matches.summary.ui.model.asProcessingSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.asProcessingSummaryDataFromPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private var matchDetailState = UIViewState<MatchDetail>(
        process = MatchDetailUIProcess.LOAD_MATCH_DETAIL,
        state = UIDataState.STARTED,
        result = null
    )
    private val _matchDetailLiveData: MutableLiveData<UIViewState<MatchDetail>> = MutableLiveData(matchDetailState)
    val matchDetailLiveData: LiveData<UIViewState<MatchDetail>> = _matchDetailLiveData

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

    fun loadMatchDetail(matchId: Int) {
        matchDetailState = matchDetailState
            .asProcessingMatchDetail()
            .withError(null)
        _matchDetailLiveData.postValue(matchDetailState)

        viewModelScope.launch (Dispatchers.IO) {
/*
            matchesRepository.requestMatchDetail(matchId).collect { response ->
                handleMatchResponse(response)
            }
*/
            delay(2500)
            handleMatchResponse(getFixtureMatchDetail())
        }
    }

    private fun getFixtureMatchDetail() = Result.success(
        MatchDetail(
            opponents = emptyList(),
            players = listOf(
                getFixturePlayer("Yfful", "Eiichiro", "Oda", "team1"),
                getFixturePlayer("Player2", "Nome", "Jogador", "team1"),
                getFixturePlayer("Player3", "Nome", "Jogador", "team1"),
                getFixturePlayer("Player4", "Nome", "Jogador", "team1"),
                getFixturePlayer("Player5", "Nome", "Jogador", "team1"),
                getFixturePlayer("Ukog", "Akira", "Toryama", "team2"),
                getFixturePlayer("Player2", "Nome", "Jogador", "team2"),
                getFixturePlayer("Player3", "Nome", "Jogador", "team2"),
                getFixturePlayer("Player4", "Nome", "Jogador", "team2"),
                getFixturePlayer("Player5", "Nome", "Jogador", "team2"),
            )
        )
    )

    private fun getFixturePlayer(
        nick: String,
        firstName: String,
        lastName: String? = null,
        slug: String,
    ): MatchPlayerResponse {
        return MatchPlayerResponse(
            id = Math.random().toInt(),
            imageUrl = null,
            firstName = firstName,
            lastName = lastName,
            nickName = nick,
            slug = slug
        )
    }

    private fun handleMatchResponse(response: Result<MatchDetail>) {
        response.getOrNull()?.let { matchDetail ->
            matchDetailState = matchDetailState
                .asLoadedMatchDetail()
                .withResult(matchDetail)
            _matchDetailLiveData.postValue(matchDetailState)
        }
    }

    fun userLeftMatchDetailScreen() {
        matchDetailState = matchDetailState
            .asResetLoadMatchDetail()
            .withResult(null)
        _matchDetailLiveData.postValue(matchDetailState)
    }
}
