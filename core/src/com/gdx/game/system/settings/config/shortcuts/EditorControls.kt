package com.gdx.game.system.settings.config.shortcuts

import com.gdx.game.system.defaults.settings.ControlsProperties
import com.gdx.game.system.defaults.settings.ControlsPropertiesDefaults
import com.gdx.game.system.settings.ValueParser
import java.util.*

class EditorControls(shortcuts: Properties) {
    var keyCodeChangeValueByFactor10 = 0
        private set
    var keyCodeSelectGameObject = 0
        private set

    init {
        initControls(shortcuts)
    }

    private fun initControls(shortcuts: Properties) {
        val parser = ValueParser()
        keyCodeChangeValueByFactor10 = parser.getInteger(shortcuts,
                ControlsProperties.Editor.CHANGE_VALUE_BY_FACTOR_10,
                ControlsPropertiesDefaults.Editor.CHANGE_VALUE_BY_FACTOR_10)

        keyCodeSelectGameObject = parser.getInteger(shortcuts,
                ControlsProperties.Editor.SELECT_GAME_OBJECT,
                ControlsPropertiesDefaults.Editor.SELECT_GAME_OBJECT)
    }
}