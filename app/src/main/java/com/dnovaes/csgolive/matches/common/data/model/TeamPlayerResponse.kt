package com.dnovaes.csgolive.matches.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamPlayerResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("name")
    val nickName: String?,
    val slug: String,
)
