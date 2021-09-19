package com.gdx.game.system.settings.config.shortcuts

import com.gdx.game.system.defaults.settings.ControlsProperties
import com.gdx.game.system.defaults.settings.ControlsPropertiesDefaults
import com.gdx.game.system.settings.ValueParser
import java.util.*

class IngameControls(shortcuts: Properties) {
    var moveCameraTop = 0
        private set
    var moveCameraRight = 0
        private set
    var moveCameraBottom = 0
        private set
    var moveCameraLeft = 0
        private set

    init {
        initControls(shortcuts)
    }

    private fun initControls(shortcuts: Properties) {
        val parser = ValueParser()
        moveCameraTop = parser.getInteger(shortcuts, ControlsProperties.Ingame.MOVE_CAMERA_TOP, ControlsPropertiesDefaults.Ingame.MOVE_CAMERA_TOP)
        moveCameraRight = parser.getInteger(shortcuts, ControlsProperties.Ingame.MOVE_CAMERA_RIGHT, ControlsPropertiesDefaults.Ingame.MOVE_CAMERA_RIGHT)
        moveCameraBottom = parser.getInteger(shortcuts, ControlsProperties.Ingame.MOVE_CAMERA_BOTTOM, ControlsPropertiesDefaults.Ingame.MOVE_CAMERA_BOTTOM)
        moveCameraLeft = parser.getInteger(shortcuts, ControlsProperties.Ingame.MOVE_CAMERA_LEFT, ControlsPropertiesDefaults.Ingame.MOVE_CAMERA_LEFT)
    }
}