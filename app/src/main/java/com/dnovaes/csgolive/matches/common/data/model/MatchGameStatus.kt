package com.dnovaes.csgolive.matches.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MatchGameStatus {
    @SerialName("rescheduled")
    RESCHEDULED,
    @SerialName("started")
    STARTED,
    @SerialName("not_started")
    NOTSTARTED,
    @SerialName("running")
    RUNNING,
    @SerialName("finished")
    FINISHED,
    @SerialName("canceled")
    CANCELED,
    @SerialName("postponed")
    POSTPONED;
}
