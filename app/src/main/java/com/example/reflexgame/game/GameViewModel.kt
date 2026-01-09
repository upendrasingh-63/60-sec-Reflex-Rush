package com.example.reflexgame.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reflexrush.game.GameEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val gameEngine = GameEngine()
    private val timerManager = TimerManager(60)

    private var maxX = 0
    private var maxY = 0

    private val OBJECT_LIFETIME = 800L
    private val SPAWN_INTERVAL = 500L

    private val _remainingTime = MutableStateFlow(60)
    val remainingTime: StateFlow<Int> = _remainingTime

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _accuracy = MutableStateFlow(0)
    val accuracy: StateFlow<Int> = _accuracy

    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> = _gameOver

    private val _objects = MutableStateFlow<List<GameObject>>(emptyList())
    val objects = _objects.asStateFlow()

    private var objectIdCounter = 0

    fun updateBounds(maxWidth: Int, maxHeight: Int) {
        maxX = maxWidth
        maxY = maxHeight
    }

    fun startGame() {
        resetGame()

        // Start spawning objects
        viewModelScope.launch {
            while (!_gameOver.value) {
                spawnObject()
                delay(SPAWN_INTERVAL)
            }
        }

        // Start timer
        timerManager.startTimer(
            scope = viewModelScope,
            onTick = { _remainingTime.value = it },
            onFinish = { endGame() }
        )
    }

    private fun spawnObject() {
        if (maxX == 0 || maxY == 0) return

        val objectSize = 120 // must match UI size

        val newObject = GameObject(
            id = objectIdCounter++,
            isCorrect = Random.nextBoolean(),
            x = Random.nextInt(0, maxX - objectSize),
            y = Random.nextInt(200, maxY - objectSize) // 200px reserved for HUD
        )

        _objects.value += newObject
        handleObjectLifetime(newObject)
    }


    private fun handleObjectLifetime(obj: GameObject) {
        viewModelScope.launch {
            delay(OBJECT_LIFETIME)

            if (_objects.value.contains(obj) && !_gameOver.value) {
                // Missed GREEN object → penalty
                if (obj.isCorrect) {
                    gameEngine.onMissedCorrectObject()
                    updateStats()
                }
                _objects.value -= obj
            }
        }
    }

    fun onObjectTapped(obj: GameObject) {
        if (_gameOver.value) return

        gameEngine.onUserTap(obj.isCorrect)
        updateStats()

        _objects.value -= obj
    }

    private fun updateStats() {
        _score.value = gameEngine.calculateScore()
        _accuracy.value = gameEngine.calculateAccuracy()
    }

    private fun endGame() {
        _gameOver.value = true
        updateStats()
    }

    private fun resetGame() {
        gameEngine.resetGame()
        timerManager.resetTimer()

        _remainingTime.value = 60
        _score.value = 0
        _accuracy.value = 0
        _gameOver.value = false
        _objects.value = emptyList()
    }
}
