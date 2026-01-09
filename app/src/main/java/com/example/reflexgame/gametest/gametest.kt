package com.example.reflexgame.gametest

import com.example.reflexrush.game.GameEngine

fun main(){
    val engine=GameEngine();

    engine.onUserTap(true);
    engine.onUserTap(true);
    engine.onUserTap(true);

    println("Score -> ${engine.calculateScore()}")
    println("Accuracy -> ${engine.calculateAccuracy()}%")
}
