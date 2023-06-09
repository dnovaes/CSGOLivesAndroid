package com.dnovaes.csgolive.matches.summary.data

import com.dnovaes.csgolive.common.data.models.DispatcherInterface
import com.dnovaes.csgolive.common.data.remote.PandaScoreAPIInterface
import com.dnovaes.csgolive.common.utilities.extensions.formatUsingLocalZoneId
import com.dnovaes.csgolive.matches.common.data.model.MatchDetail
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import com.dnovaes.csgolive.matches.common.data.model.TeamInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchesRepository(
    private val api: PandaScoreAPIInterface,
    private val dispatcher: DispatcherInterface,
) {

    fun requestMatchesList(page: Int = 1): Flow<Result<List<MatchResponse>>> {
        return flow {
            runCatching {
                api.getCSGOMatchesList(
                    filterStatus = "running, not_started",
                    finished = false,
                    sort = "begin_at",
                    page = page,
                ).mapDatesWithLocalZonedId()
            }.onFailure {
                println("logd MatchesList - onFailure) cause: ${it.cause}\n\tmessage: ${it.message}\n")
                println("logd MatchesList - onFailure) stackTrace: ${it.stackTrace.first()}")
                //emit(processErrorResponse<CardResponse>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }

    fun requestTeamInfo(idList: List<Int>): Flow<Result<List<TeamInfoResponse>>> {
        return flow {
            runCatching {
                api.getTeamInfo(
                    id = idList.joinToString(",")
                )
            }.onFailure {
                println("logd MatchesList - onFailure) cause: ${it.cause}\n\tmessage: ${it.message}\n")
                println("logd MatchesList - onFailure) stackTrace: ${it.stackTrace.first()}")
                emit(Result.failure<List<TeamInfoResponse>>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }

    //TODO: Add to mapper class
    private fun List<MatchResponse>.mapDatesWithLocalZonedId(): List<MatchResponse> {
        val formattedMatchesModel = mutableListOf<MatchResponse>()
        this.forEach {
            val formattedLocalDateTime = it.beginAt?.formatUsingLocalZoneId()
            formattedMatchesModel.add(it.copy(beginAt = formattedLocalDateTime))
        }
        return formattedMatchesModel.toList()
    }

    fun requestMatchDetail(matchId: Int): Flow<Result<MatchDetail>> {
        return flow {
            runCatching {
                api.getMatch(matchId)
            }.onFailure {
                println("logd MatchesList - onFailure) cause: ${it.cause}\n\tmessage: ${it.message}\n")
                println("logd MatchesList - onFailure) stackTrace: ${it.stackTrace.first()}")
                emit(Result.failure<MatchDetail>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }
}
