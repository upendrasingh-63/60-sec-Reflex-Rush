package com.example.reflexgame.leaderboard

data class LeaderboardEntry(
    val rank: Int,
    val username: String,
    val score: Int,
    val accuracy: Int
)
