package com.dnovaes.csgolive.matches.summary.data

import com.dnovaes.csgolive.common.data.models.DispatcherInterface
import com.dnovaes.csgolive.common.data.remote.PandaScoreAPIInterface
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchesRepository(
    private val api: PandaScoreAPIInterface,
    private val dispatcher: DispatcherInterface,
) {

    fun requestMatchesList(): Flow<Result<List<MatchResponse>>> {
        return flow {
            runCatching {
                api.getMatchesList()
            }.onFailure {
                println("logd MatchesList - onFailure) cause: ${it.cause}\n\tmessage: ${it.message}\n")
                println("logd MatchesList - onFailure) stackTrace: ${it.stackTrace.first()}")
                //emit(processErrorResponse<CardResponse>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }
}