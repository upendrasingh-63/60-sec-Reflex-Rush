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
import com.example.reflexgame.game.GameObject
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.reflexgame.navigation.Screen


@Composable
fun ReflexRushScreen(
    navController: NavController,
    gameViewModel: GameViewModel = viewModel()
) {

    val time by gameViewModel.remainingTime.collectAsState()
    val objects by gameViewModel.objects.collectAsState()
    val score by gameViewModel.score.collectAsState()
    val accuracy by gameViewModel.accuracy.collectAsState()
    val gameOver by gameViewModel.gameOver.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        gameViewModel.startGame()
    }

    LaunchedEffect(gameOver) {
        if (gameOver) {
            Toast.makeText(
                context,
                "Score submitted!",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Screen.Leaderboard.route) {
                popUpTo(Screen.Game.route) { inclusive = true }
            }
        }
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
                objects = objects,
                onObjectTap = { obj ->
                    gameViewModel.onObjectTapped(obj)
                },
                onBoundsReady = { width, height ->
                    gameViewModel.updateBounds(width, height)
                }
            )
        }
    }
}


@Composable
fun GamePlayScreen(
    time: Int,
    score: Int,
    accuracy: Int,
    objects: List<GameObject>,
    onObjectTap: (GameObject) -> Unit,
    onBoundsReady: (Int, Int) -> Unit
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight
        LaunchedEffect(maxWidth, maxHeight) {
            onBoundsReady(maxWidth, maxHeight)
        }

        // Top HUD
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Time: $time", color = Color.White)
            Text("Score: $score", color = Color.Green)
            Text("Accuracy: $accuracy%", color = Color.Cyan)
        }

        // 🎯 MULTIPLE OBJECTS
        objects.forEach { obj ->
            Box(
                modifier = Modifier
                    .offset { IntOffset(obj.x, obj.y) }
                    .size(120.dp)
                    .background(
                        color = if (obj.isCorrect) Color.Green else Color.Red,
                        shape = CircleShape
                    )
                    .clickable { onObjectTap(obj) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (obj.isCorrect) "HIT" else "AVOID",
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
