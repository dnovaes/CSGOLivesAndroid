package com.dnovaes.csgolive.matches.common.data.model

import java.time.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchResponse(
    val id: Long,
    val name: String?,
    @Contextual
    @SerialName("begin_at") val beginAt: LocalDateTime,
    @SerialName("end_at") val endAt: String?,
    @SerialName("tournament_id") val tournamentId: Long,
    val status: MatchGameStatus,
    @SerialName("winner_id") val winnerId: Long?,
    val league: MatchLeagueResponse,
    val serie: MatchSerieResponse,
    val videogame: MatchVideoGameResponse,
    val opponents: List<MatchOpponentGroupResponse>,
)

fun List<MatchOpponentGroupResponse>.getItemNameOrDefault(pos: Int): String {
    return this.getOrNull(pos)?.opponent?.name ?: "?"
}

fun List<MatchOpponentGroupResponse>.getImageUrlOrNull(pos: Int)
    = getOrNull(pos)?.opponent?.imageUrl

@kotlinx.serialization.Serializable
data class MatchLeagueResponse (
    val id: Long,
    val name: String,
    @SerialName("image_url") val imageUrl: String?
)

@kotlinx.serialization.Serializable
data class MatchSerieResponse(
   val name: String?,
   @SerialName("full_name") val fullName: String
)

@kotlinx.serialization.Serializable
data class MatchVideoGameResponse(
    val id: Long,
    val name: MatchGameName,
    val slug: String
)

@kotlinx.serialization.Serializable
data class MatchOpponentGroupResponse(
    val opponent: MatchOpponentResponse,
    val type: String
)

@kotlinx.serialization.Serializable
data class MatchOpponentResponse(
    val id: Long,
    val name: String,
    @SerialName("image_url") val imageUrl: String?,
    val slug: String
)