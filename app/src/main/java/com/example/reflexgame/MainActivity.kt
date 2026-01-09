package com.example.reflexgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import com.example.reflexgame.game.ReflexRushScreen
import com.example.reflexgame.leaderboard.LeaderboardScreen
import com.example.reflexgame.navigation.Screen
import com.example.reflexgame.ui.theme.ReflexGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReflexGameTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Game.route
                ) {
                    composable(Screen.Game.route) {
                        ReflexRushScreen(navController = navController)
                    }
                    composable(Screen.Leaderboard.route) {
                        LeaderboardScreen()
                    }
                }
            }
        }
    }
}
