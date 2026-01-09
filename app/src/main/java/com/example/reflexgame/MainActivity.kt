package com.example.reflexgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.reflexgame.game.ReflexRushScreen
import com.example.reflexgame.leaderboard.LeaderboardScreen
import com.example.reflexgame.ui.theme.ReflexGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReflexGameTheme {
                LeaderboardScreen()
            }
        }
    }
}