package com.example.reflexgame.game


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reflexrush.game.GameEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class GameViewModel : ViewModel() {

    private val gameEngine = GameEngine()
    private val timerManager = TimerManager(60)

    private val _remainingTime = MutableStateFlow(60)
    val remainingTime: StateFlow<Int> = _remainingTime

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _accuracy = MutableStateFlow(0)
    val accuracy: StateFlow<Int> = _accuracy

    private val OBJECT_LIFETIME = 800L // milliseconds

    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> = _gameOver

    private val _currentObject = MutableStateFlow<GameObject?>(null)
    val currentObject = _currentObject.asStateFlow()


    fun startGame() {
        resetGame()

        timerManager.startTimer(
            scope = viewModelScope,
            onTick = { time ->
                _remainingTime.value = time
            },
            onFinish = {
                endGame()
            }
        )
        generateNewObject()
    }

    private fun generateNewObject() {
        viewModelScope.launch {
            val isCorrect = kotlin.random.Random.nextBoolean()
            _currentObject.value = GameObject(isCorrect)

            delay(OBJECT_LIFETIME)

            // If object still exists and it was GREEN, user missed it
            if (_currentObject.value?.isCorrect == true && !_gameOver.value) {
                gameEngine.onMissedCorrectObject()
                updateStats()
            }

            if (!_gameOver.value) {
                generateNewObject()
            }
        }
    }

    fun onUserTap() {
        if (_gameOver.value) return

        val obj = _currentObject.value ?: return

        gameEngine.onUserTap(obj.isCorrect)
        updateStats()

        // Remove current object immediately
        _currentObject.value = null
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
    }

}


