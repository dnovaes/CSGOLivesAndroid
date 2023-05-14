package com.dnovaes.csgolive.common.data.remote

import kotlinx.serialization.json.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

const val PANDASCORE_SERVICE_URL = "https://api.pandascore.co/"

interface PandaScoreAPIInterface{

    /*
    https://developers.pandascore.com/reference/get_matches
    */
    @GET("/matches/")
    suspend fun getMatchesList(
        @Query("sort") sort: String,
        @Query("page") page: String,
        @Query("per_page") id: String
    ): JsonObject

}