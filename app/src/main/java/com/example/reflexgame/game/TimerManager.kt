package com.example.reflexgame.game

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerManager(
    private val totalTimeInSeconds: Int = 60
) {

    private var isRunning = false

    fun startTimer(
        scope: CoroutineScope,
        onTick: (Int) -> Unit,
        onFinish: () -> Unit
    ) {
        if (isRunning) return

        isRunning = true

        scope.launch {
            var remainingTime = totalTimeInSeconds

            while (remainingTime > 0 && isRunning) {
                onTick(remainingTime)
                delay(1000)
                remainingTime--
            }

            if (isRunning) {
                onTick(0)
                onFinish()
            }

            isRunning = false
        }
    }

    fun stopTimer() {
        isRunning = false
    }

    fun resetTimer() {
        isRunning = false
    }
}