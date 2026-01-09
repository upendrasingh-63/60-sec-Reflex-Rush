package com.example.reflexgame.navigation

sealed class Screen(val route: String) {
    object Game : Screen("game")
    object Leaderboard : Screen("leaderboard")
}