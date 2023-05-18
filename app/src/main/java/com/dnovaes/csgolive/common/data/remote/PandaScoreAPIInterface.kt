package com.dnovaes.csgolive.common.data.remote

import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val PANDASCORE_SERVICE_URL = "https://api.pandascore.co/"

interface PandaScoreAPIInterface{

    /*
    https://developers.pandascore.com/reference/get_matches
    */
    @GET("/matches/")
    suspend fun getMatchesList(
        @Query("filter[videogame]") filterGameId: String,
        //@Query("filter[status]") status: String = "running",
        @Query("finished") finished: Boolean,
        @Query("sort") sort: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50,
    ): List<MatchResponse>

    /*
    https://developers.pandascore.co/reference/get_csgo_matches
     */

    @GET("/csgo/matches/")
    suspend fun getCSGOMatchesList(
        @Query("filter[status]") filterStatus: String,
        @Query("finished") finished: Boolean,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
    ): List<MatchResponse>
}
