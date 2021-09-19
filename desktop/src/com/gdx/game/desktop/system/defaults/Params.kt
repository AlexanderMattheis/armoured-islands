package com.gdx.game.desktop.system.defaults

import com.badlogic.gdx.math.Vector2

interface Params {
    object Window {
        object Icon {
            const val DATA_TYPE = ".png"
            const val NAME = "tank"
            const val PATH = ""
            val RESOLUTIONS = intArrayOf(16, 32, 48)
        }

        val DIMENSIONS = Vector2(1280f, 720f)
        const val TITLE = "Armoured Islands"
    }
}