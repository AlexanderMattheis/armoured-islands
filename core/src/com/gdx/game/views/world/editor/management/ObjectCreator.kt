package com.gdx.game.views.world.editor.management

import com.gdx.game.system.defaults.views.world.objects.CharacterDefaults
import com.gdx.game.system.defaults.views.world.objects.ItemDefaults
import com.gdx.game.system.defaults.views.world.objects.ObstacleDefaults
import com.gdx.game.system.defaults.views.world.objects.SurfaceDefaults
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import com.gdx.game.views.world.objects.elements.references.TextureReference
import com.gdx.game.views.world.objects.types.Character
import com.gdx.game.views.world.objects.types.Item
import com.gdx.game.views.world.objects.types.Obstacle
import com.gdx.game.views.world.objects.types.Surface

class ObjectCreator(private val texturesCache: MapTexturesCache) {

    fun create(selectedType: ObjectType, objectName: String): GameObject {
        return when (selectedType) {
            ObjectType.CHARACTERS -> createNewCharacter(texturesCache, objectName)
            ObjectType.ITEMS -> createNewItem(texturesCache, objectName)
            ObjectType.OBSTACLES -> createNewObstacle(texturesCache, objectName)
            ObjectType.SURFACES -> createNewSurface(texturesCache, objectName)
            else -> throw IllegalArgumentException(Errors.NOT_EXISTENT_TYPE)
        }
    }

    private fun createNewCharacter(cache: MapTexturesCache, objectName: String): GameObject {
        val textureReference = TextureReference(objectName, 0, false)
        val character = Character(textureReference, null, CharacterDefaults.POSITION, CharacterDefaults.ROTATION, CharacterDefaults.MOVEMENT)
        cache.addToCharactersTextureCache(character)
        return character
    }

    private fun createNewItem(cache: MapTexturesCache, objectName: String): GameObject {
        val textureReference = TextureReference(objectName, 0, false)
        val item = Item(textureReference, null, ItemDefaults.POSITION)
        cache.addToItemsTextureCache(item)
        return item
    }

    private fun createNewObstacle(cache: MapTexturesCache, objectName: String): GameObject {
        val textureReference = TextureReference(objectName, 0, false)
        val obstacle = Obstacle(textureReference, null, ObstacleDefaults.POSITION, ObstacleDefaults.ROTATION)
        cache.addToObstaclesTextureCache(obstacle)
        return obstacle
    }

    private fun createNewSurface(cache: MapTexturesCache, objectName: String): GameObject {
        val textureReference = TextureReference(objectName, 0, false)
        val surface = Surface(textureReference, null, SurfaceDefaults.POSITION, SurfaceDefaults.ROTATION, SurfaceDefaults.MIRRORED)
        cache.addToSurfacesTextureCache(surface)
        return surface
    }
}