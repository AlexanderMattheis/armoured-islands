package com.gdx.game.views.world.editor.management

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import com.gdx.game.views.world.objects.types.Surface

class ObjectManager(private val map: Map, private val texturesCache: MapTexturesCache) {

    // TODO: create NEW mirrored object?
    fun mirrorObject(gameObject: GameObject?) {
        if (gameObject !is Surface) { // it is only allowed to mirror surfaces
            return
        }

        val position = gameObject.position
        val surface = map.getSurface(Vector2(position.x, position.y)) as Surface

        texturesCache.getTexture(ObjectType.SURFACES, surface.id).flip(true, false)
        surface.isMirrored = !surface.isMirrored // necessary, to store the map correctly as file

        if (surface.rotation == 90) {
            surface.rotation = 270
        } else if (surface.rotation == 270) {
            surface.rotation = 90
        }
    }

    // TODO: create NEW rotated object?
    fun rotateObject(gameObject: GameObject?) {
        if (gameObject is Surface) {  // it is only allowed to mirror surfaces
            val position = gameObject.position
            val surface = map.getSurface(Vector2(position.x, position.y)) as Surface
            surface.rotation = (surface.rotation + 90) % 360
        }
    }
}
