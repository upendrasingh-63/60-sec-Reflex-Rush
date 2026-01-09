package com.example.reflexgame.game


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReflexRushScreen(
    gameViewModel: GameViewModel = viewModel()
) {

    val time by gameViewModel.remainingTime.collectAsState()
    val gameObject by gameViewModel.currentObject.collectAsState()
    val score by gameViewModel.score.collectAsState()
    val accuracy by gameViewModel.accuracy.collectAsState()
    val gameOver by gameViewModel.gameOver.collectAsState()

    LaunchedEffect(Unit) {
        gameViewModel.startGame()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        if (gameOver) {
            GameOverScreen(
                score = score,
                accuracy = accuracy,
                onRestart = { gameViewModel.startGame() }
            )
        } else {
            GamePlayScreen(
                time = time,
                score = score,
                accuracy = accuracy,
                gameObject = gameObject,
                onTap = { gameViewModel.onUserTap() }
            )
        }
    }
}


@Composable
fun GamePlayScreen(
    time: Int,
    score: Int,
    accuracy: Int,
    gameObject: GameObject?,
    onTap: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight

        // Random position remembered per object
        val offsetX = remember(gameObject) {
            (0..(maxWidth - 120)).random()
        }
        val offsetY = remember(gameObject) {
            (200..(maxHeight - 120)).random()
        }

        // Top HUD
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Time: $time", color = Color.White)
            Text("Score: $score", color = Color.Green)
            Text("Accuracy: $accuracy%", color = Color.Cyan)
        }

        // 🎯 Random Object
        if (gameObject != null) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX, offsetY) }
                    .size(120.dp)
                    .background(
                        color = if (gameObject.isCorrect) Color.Green else Color.Red,
                        shape = CircleShape
                    )
                    .clickable { onTap() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (gameObject.isCorrect) "HIT" else "AVOID",
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}


@Composable
fun GameOverScreen(
    score: Int,
    accuracy: Int,
    onRestart: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Game Over",
            color = Color.Red,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Final Score: $score",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Accuracy: $accuracy%",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onRestart) {
            Text("Play Again")
        }
    }
}
