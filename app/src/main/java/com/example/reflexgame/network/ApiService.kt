package com.example.reflexgame.network

import com.example.reflexgame.leaderboard.LeaderboardEntry
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("leaderboard/top")
    suspend fun getLeaderboard(): List<LeaderboardEntry>

    @POST("game/score")
    suspend fun submitScore(
        @Body body: ScoreRequest
    )

}
