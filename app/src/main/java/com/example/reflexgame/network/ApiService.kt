package com.example.reflexgame.network

import com.example.reflexgame.leaderboard.LeaderboardEntry
import retrofit2.http.GET

interface ApiService {

    @GET("leaderboard/top")
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}
