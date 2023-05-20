package com.dnovaes.csgolive.matches.common.data.model

import com.dnovaes.csgolive.matches.common.ui.model.UIModelInterface
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamInfoResponse(
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String?,
    val name: String,
    val players: List<TeamPlayerResponse>,
    val slug: String,
): UIModelInterface
