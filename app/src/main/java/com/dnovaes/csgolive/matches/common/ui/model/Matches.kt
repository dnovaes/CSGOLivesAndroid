package com.dnovaes.csgolive.matches.common.ui.model

import com.dnovaes.csgolive.matches.common.data.model.MatchResponse

data class Matches(
    val amountPagesLoaded: Int = 0,
    val data: List<MatchResponse>,
) : UIModelInterface
