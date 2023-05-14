package com.dnovaes.csgolive.common.data.remote

import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import retrofit2.http.GET

const val PANDASCORE_SERVICE_URL = "https://api.pandascore.co/"

interface PandaScoreAPIInterface{

    /*
    https://developers.pandascore.com/reference/get_matches
    */
    @GET("/matches/")
    suspend fun getMatchesList(): List<MatchResponse>

    //@Query("sort") sort: String,
    //@Query("page") page: String,
    //@Query("per_page") id: String
}