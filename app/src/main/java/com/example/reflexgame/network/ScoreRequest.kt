package com.example.reflexgame.network

data class ScoreRequest(
    val username: String,
    val score: Int,
    val accuracy: Int
)
