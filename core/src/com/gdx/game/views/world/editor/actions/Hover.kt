package com.gdx.game.views.world.editor.actions

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.MapEditor
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.types.Surface

class Hover(settings: Settings) : PlaygroundAction(settings) {

    fun execute(cameraPosition: Vector2, mouseX: Int, mouseY: Int, map: Map, editor: MapEditor, gameObject: GameObject?) {
        val tilePosition = getTilePositionAtMousePointer(cameraPosition, mouseX, mouseY)

        if (gameObject == null || !isPositionWithinMap(tilePosition, map.getDimensions())) {
            editor.hoveredObject = null
            return
        }

        editor.isObjectPlaceable = map.getObjects(tilePosition).size == 1 || gameObject is Surface
        editor.hoveredObject = map.getSurface(tilePosition)
    }
}