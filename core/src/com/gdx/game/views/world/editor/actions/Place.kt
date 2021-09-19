package com.gdx.game.views.world.editor.actions

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.management.tracking.MapChange
import com.gdx.game.views.world.editor.management.tracking.changes.PlacedObject
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.types.Character
import com.gdx.game.views.world.objects.types.Item
import com.gdx.game.views.world.objects.types.Obstacle
import com.gdx.game.views.world.objects.types.Surface
import java.util.*

class Place(settings: Settings) : PlaygroundAction(settings) {

    fun execute(cameraPosition: Vector2, mouseX: Int, mouseY: Int, map: Map, gameObject: GameObject): MapChange? {
        val tilePosition = getTilePositionAtMousePointer(cameraPosition, mouseX, mouseY)

        if (!isPositionWithinMap(tilePosition, map.getDimensions())) {
            return null
        }

        gameObject.position = Vector2(tilePosition.x, tilePosition.y) // this has to be executed before adding to the map
        return PlacedObject(cameraPosition, tilePosition, addObjectToMap(map, gameObject, tilePosition), gameObject)
    }

    private fun addObjectToMap(map: Map, gameObject: GameObject, tilePosition: Vector2): List<GameObject> {
        val isFree = isOnlySurfaceOrDecorBelow(map, tilePosition)
        val removedObjects: MutableList<GameObject> = ArrayList()

        when {
            gameObject is Character && isFree -> map.addCharacter(gameObject)
            gameObject is Item && isFree -> map.addItem(gameObject)
            gameObject is Obstacle && isFree -> map.addObstacle(gameObject)
            gameObject is Surface -> {
                removedObjects.addAll(map.popObjects(tilePosition))
                map.addSurface(gameObject)
            }
        }

        return removedObjects
    }

    private fun isOnlySurfaceOrDecorBelow(map: Map, position: Vector2): Boolean {
        val objects = map.getObjects(position)

        if (objects.size > 1) {  // TODO: > 2
            return false
        }

        for (gameObject in objects) {
            if (gameObject !is Surface) {  // TODO: && gameObject !is Decor
                return false
            }
        }

        return true
    }
}