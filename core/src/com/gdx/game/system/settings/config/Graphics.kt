package com.gdx.game.system.settings.config

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.settings.SettingsProperties
import com.gdx.game.system.defaults.settings.SettingsPropertiesDefaults
import com.gdx.game.system.settings.ValueParser
import java.util.*

class Graphics(settings: Properties) {
    private var vSync = false
    private var resolution: Vector2? = null
    var textureSize = 0
        private set

    init {
        initGraphics(settings)
    }

    private fun initGraphics(settings: Properties) {
        val parser = ValueParser()
        vSync = parser.getBoolean(settings, SettingsProperties.Graphics.V_SYNC, SettingsPropertiesDefaults.Graphics.V_SYNC)
        resolution = parser.getDimensions(settings,
                SettingsProperties.Graphics.RESOLUTION_X,
                SettingsProperties.Graphics.RESOLUTION_Y,
                SettingsPropertiesDefaults.Graphics.RESOLUTION)
        textureSize = parser.getInteger(settings, SettingsProperties.Graphics.TEXTURE_SIZE, SettingsPropertiesDefaults.Graphics.TEXTURE_SIZE)
    }
}