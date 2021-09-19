package com.gdx.game.views.screens

interface Screen {
    fun update(deltaTime: Float)
    fun draw(deltaTime: Float)
}