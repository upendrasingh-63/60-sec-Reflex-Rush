package com.example.reflexrush.game

class GameEngine {

    // Game stats
    private var correctTaps = 0
    private var wrongTaps = 0

    // Difficulty control
    private var level = 1
    private var speedMultiplier = 1.0f

    // Called when user taps a target
    fun onUserTap(isCorrectTarget: Boolean) {
        if (isCorrectTarget) {
            correctTaps++
            increaseDifficultyIfNeeded()
        } else {
            wrongTaps++
        }
    }

    fun onMissedCorrectObject() {
        // Missed green object = penalty
        wrongTaps++
    }


    // Score calculation logic
    fun calculateScore(): Int {
        val baseScore = (correctTaps * 10) - (wrongTaps * 5)
        return (baseScore * speedMultiplier).toInt().coerceAtLeast(0)
    }

    // Accuracy calculation (optional but useful)
    fun calculateAccuracy(): Int {
        val totalTaps = correctTaps + wrongTaps
        if (totalTaps == 0) return 0
        return ((correctTaps.toFloat() / totalTaps) * 100).toInt()
    }

    // Difficulty increases every 10 correct taps
    private fun increaseDifficultyIfNeeded() {
        if (correctTaps % 10 == 0) {
            level++
            speedMultiplier += 0.1f
        }
    }

    // Expose current difficulty (for UI later)
    fun getCurrentLevel(): Int = level

    // Reset game for a new session
    fun resetGame() {
        correctTaps = 0
        wrongTaps = 0
        level = 1
        speedMultiplier = 1.0f
    }
}
