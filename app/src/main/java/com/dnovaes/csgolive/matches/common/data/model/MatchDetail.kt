package com.dnovaes.csgolive.matches.common.data.model

import com.dnovaes.csgolive.matches.common.ui.model.UIModelInterface
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDetail(
    val opponents: List<MatchOpponentGroupResponse>,
    val players: List<MatchPlayerResponse>
): UIModelInterface

@Serializable
data class MatchPlayerResponse(
    @SerialName("player_id")
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("name")
    val nickName: String,
    val slug: String,
)
