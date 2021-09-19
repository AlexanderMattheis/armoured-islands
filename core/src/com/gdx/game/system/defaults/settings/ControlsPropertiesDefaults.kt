package com.gdx.game.system.defaults.settings

import com.badlogic.gdx.Input

object ControlsPropertiesDefaults {
    object Editor {
        const val CHANGE_VALUE_BY_FACTOR_10 = Input.Keys.SHIFT_LEFT
        const val SELECT_GAME_OBJECT = Input.Keys.ALT_LEFT
    }

    object Ingame {
        const val MOVE_CAMERA_TOP = Input.Keys.UP
        const val MOVE_CAMERA_RIGHT = Input.Keys.RIGHT
        const val MOVE_CAMERA_BOTTOM = Input.Keys.DOWN
        const val MOVE_CAMERA_LEFT = Input.Keys.LEFT
    }
}