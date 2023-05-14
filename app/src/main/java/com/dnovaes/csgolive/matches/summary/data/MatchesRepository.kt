package com.dnovaes.csgolive.matches.summary.data

import com.dnovaes.csgolive.common.data.models.DispatcherInterface
import com.dnovaes.csgolive.common.data.remote.PandaScoreAPIInterface
import com.dnovaes.csgolive.matches.summary.data.model.MatchesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchesRepository(
    private val dispatcher: DispatcherInterface
) {

    fun requestMatchesList(): Flow<Result<MatchesResponse>> {
        return flow {
            runCatching {
                MatchesResponse("")
                //pandaScoreAPI.getMatchesList(id)
            }.onFailure {
                //emit(processErrorResponse<CardResponse>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }
}