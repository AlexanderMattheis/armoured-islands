package com.gdx.game.views.world.editor.actions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.settings.config.Settings
import kotlin.math.floor

open class PlaygroundAction constructor(settings: Settings) {

    private val tileSize: Int = settings.graphics.textureSize

    /**
     * The camera-pivot is in the screen center. That's why half of the width/height is subtracted.
     * The mouse is measured from the top-left corner, but the map-position and camera-position
     * from the bottom-left corner.
     */
    fun getTilePositionAtMousePointer(cameraPosition: Vector2, mouseX: Int, mouseY: Int): Vector2 {
        val screenWidthPixel = Gdx.graphics.width
        val screenHeightPixel = Gdx.graphics.height

        val xMapPixel = cameraPosition.x - screenWidthPixel / 2f + mouseX
        // cameraPosition.y - screenHeightPixel / 2f + screenHeightPixel - mouseY
        // cameraPosition.y - screenHeightPixel / 2f: to measure from the edge of the camera rectangle
        // screenHeightPixel - mouseY: the mouse-coordinate measured from the bottom edge of the camera rectangle
        val yMapPixel = cameraPosition.y + screenHeightPixel / 2f - mouseY
        return Vector2(floor(xMapPixel / tileSize), floor(yMapPixel / tileSize))
    }

    fun isPositionWithinMap(position: Vector2, mapDimensions: Vector2): Boolean {
        val isWithinMapX = position.x >= 0 && position.x < mapDimensions.x
        val isWithinMapY = position.y >= 0 && position.y < mapDimensions.y
        return isWithinMapX && isWithinMapY
    }
}