package com.gdx.game.desktop

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.gdx.game.system.settings.OsDetector

object SpritesheetCreator {
    enum class SpritesheetType(val typeName: String) {
        CHARACTERS("characters"),
        ITEMS("items"),
        OBSTACLES("obstacles"),
        SURFACES("surfaces");
    }

    val TYPES = arrayOf(SpritesheetType.CHARACTERS, SpritesheetType.ITEMS, SpritesheetType.OBSTACLES, SpritesheetType.SURFACES)

    private val TEXTURES_PATH = if (OsDetector.isWindows) "..\\..\\design\\textures" else "../../design/textures"

    fun createSpritesheets(types: Array<SpritesheetType>) {
        for (type in types) {
            val typeName = type.typeName
            createSpritesheet(type)
        }
    }

    fun createSpritesheet(type: SpritesheetType) {
        val typeName = type.typeName
        createSpritesheet(TEXTURES_PATH + (if (OsDetector.isWindows) "//" else "/") + typeName, TEXTURES_PATH, typeName)
    }

    private fun createSpritesheet(texturesPath: String, outputPath: String, outputName: String) {
        val settings = TexturePacker.Settings()

        settings.edgePadding = true // small margin between textures
        settings.duplicatePadding = true
        settings.maxWidth = 4096
        settings.maxHeight = 4096
        settings.filterMin = Texture.TextureFilter.Nearest
        settings.filterMag = Texture.TextureFilter.Nearest
        settings.scale = floatArrayOf(0.1f)

        TexturePacker.process(settings, texturesPath, outputPath, outputName)
    }
}