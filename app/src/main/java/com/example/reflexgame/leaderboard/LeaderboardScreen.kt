package com.example.reflexgame.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel = viewModel()
) {

    val leaderboard by viewModel.leaderboard.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(50.dp)
    ) {

        Text(
            text = "🌍 Global Leaderboard",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        val leaderboard by viewModel.leaderboard.collectAsState()
        val loading by viewModel.loading.collectAsState()
        val error by viewModel.error.collectAsState()

        when {
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Text(
                    text = "Error: $error",
                    color = Color.Red
                )
            }

            else -> {
                LazyColumn {
                    items(leaderboard) { entry ->
                        LeaderboardItem(entry)
                    }
                }
            }
        }
    }
}
@Composable
fun LeaderboardItem(entry: LeaderboardEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.DarkGray)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "#${entry.rank}",
            color = Color.Yellow,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = entry.username,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Score: ${entry.score}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "${entry.accuracy}%",
            color = Color.Cyan,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
