package com.dnovaes.csgolive.matches.common.data.model

import com.dnovaes.csgolive.matches.common.ui.model.UIModelInterface
import kotlinx.serialization.Serializable

@Serializable
data class MatchDetail(
    val team1: TeamInfoResponse,
    val team2: TeamInfoResponse,
): UIModelInterface
