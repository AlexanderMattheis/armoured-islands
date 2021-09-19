package com.gdx.game.views.world.editor.actions

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.MapEditor
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.types.Surface
import java.util.stream.Collectors

class Select(settings: Settings) : PlaygroundAction(settings) {

    fun execute(cameraPosition: Vector2, mouseX: Int, mouseY: Int, map: Map, editor: MapEditor) {
        val tilePosition = getTilePositionAtMousePointer(cameraPosition, mouseX, mouseY)

        if (!isPositionWithinMap(tilePosition, map.getDimensions())) {
            return
        }

        val lastMarkedObject = editor.selectedObject  // positions-only not working here

        if (lastMarkedObject == null || lastMarkedObject.position.x != tilePosition.x || lastMarkedObject.position.y != tilePosition.y) {
            val surface = map.getSurface(tilePosition)
            editor.selectedObject = surface
        } else if (lastMarkedObject is Surface) {
            val nonSurface = getNonSurface(map, tilePosition)
            editor.selectedObject = nonSurface
        } else {  // TODO: add Decor-class
            editor.selectedObject = null
        }
    }

    private fun getNonSurface(map: Map, position: Vector2): GameObject? {
        val gameObjects = map.getObjects(position).filter { gameObject: GameObject -> gameObject !is Surface }
        return if (gameObjects.isEmpty()) null else gameObjects[0]
    }
}