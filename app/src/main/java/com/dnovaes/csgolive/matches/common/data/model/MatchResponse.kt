package com.dnovaes.csgolive.matches.common.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MatchResponse(
    val id: Long,
    val name: String?,
    @SerialName("begin_at") val beginAt: String,
    @SerialName("end_at") val endAt: String?,
    @SerialName("tournament_id") val tournamentId: Long,
    val status: String,
    @SerialName("winner_id") val winnerId: Long?,
    val league: MatchLeagueResponse,
    val serie: MatchSerieResponse,
    val videogame: MatchVideoGameResponse,
    val opponents: List<MatchOpponentGroupResponse>,
)

fun List<MatchOpponentGroupResponse>.getItemNameOrDefault(pos: Int): String {
    return this.getOrNull(pos)?.opponent?.name ?: "Team name not assigned"
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