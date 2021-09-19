package com.gdx.game.system.defaults.views.world

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.FileExtensions

object MapArchiveDefaults {
    const val MAP_MIN_SIZE = 1
    const val MAP_MAX_SIZE = 200
    const val MAP_NAME = "map" + FileExtensions.MAP
    const val TEMP_NAME_PREFIX_SEPARATOR = "_"
    const val THUMBNAIL_NAME = "thumbnail" + FileExtensions.THUMBNAIL
    val THUMBNAIL_DIMENSION = Vector2(512f, 512f)  // high resolution necessary for 4K and 8K monitors
}