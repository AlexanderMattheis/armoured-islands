package com.gdx.game.system.storage.mapping.loader

import com.badlogic.gdx.math.Vector2

class MapMetaData(val name: String, width: Int, height: Int) : Data {
    val dimensions: Vector2 = Vector2(width.toFloat(), height.toFloat())
}