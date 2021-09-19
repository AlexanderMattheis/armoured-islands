package com.gdx.game.system.defaults.views.world

import com.badlogic.gdx.graphics.Color

object EditorDefaults {
    const val MAP_HEIGHT = 19
    const val MAP_WIDTH = 15
    const val EMPTY_MAP_NAME = "Unknown"

    object MarkerColoring {
        val ERROR = Color(0.82f, 0.14f, 0.17f, 1.0f)
        val HOVER = Color(0.5f, 0.87f, 0.13f, 1.0f)
        val SELECT = Color(0f, 0.64f, 1.0f, 1.0f)
    }
}