package com.gdx.game.system.storage.mapping.textures

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gdx.game.system.defaults.messages.errors.Errors.NOT_EXISTENT_TYPE
import com.gdx.game.system.defaults.messages.Warnings.GAME_OBJECT_NOT_FOUND
import com.gdx.game.system.defaults.Paths
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import com.gdx.game.views.world.objects.types.Surface
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Level
import java.util.logging.Logger

class MapTexturesCache(manager: AssetManager, map: com.gdx.game.views.world.Map) {
    companion object {
        private val LOGGER = Logger.getLogger(MapTexturesCache::class.qualifiedName)
        private const val FRAME_TIME = 0.2f
    }

    private val charactersTextureCache: GameObjectTextureCache
    private val itemsTextureCache: GameObjectTextureCache
    private val obstaclesTextureCache: GameObjectTextureCache
    private val surfaceTextureCache: GameObjectTextureCache

    private val characterAtlas: TextureAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.CHARACTERS_ATLAS, TextureAtlas::class.java)
    private val itemsAtlas: TextureAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.ITEMS_ATLAS, TextureAtlas::class.java)
    private val obstaclesAtlas: TextureAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.OBSTACLES_ATLAS, TextureAtlas::class.java)
    private val surfacesAtlas: TextureAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.SURFACES_ATLAS, TextureAtlas::class.java)

    init {
        charactersTextureCache = cacheTextures(characterAtlas, map.getCharacters(), ObjectType.CHARACTERS)
        itemsTextureCache = cacheTextures(itemsAtlas, map.getItems(), ObjectType.ITEMS)
        obstaclesTextureCache = cacheTextures(obstaclesAtlas, map.getObstacles(), ObjectType.OBSTACLES)
        surfaceTextureCache = cacheTextures(surfacesAtlas, map.getSurfaces(), ObjectType.SURFACES)
    }

    private fun cacheTextures(atlas: TextureAtlas, objects: List<GameObject>, type: ObjectType): GameObjectTextureCache {
        val regions: MutableMap<Int, TextureRegion> = HashMap()
        val animations: MutableMap<Int, Animation<AtlasRegion>> = HashMap()

        for (gameObject in objects) {
            addToTextureCache(atlas, gameObject, type, regions, animations)
        }

        return GameObjectTextureCache(regions, animations)
    }

    private fun addToTextureCache(atlas: TextureAtlas, gameObject: GameObject, type: ObjectType,
                                  regions: MutableMap<Int, TextureRegion>, animations: MutableMap<Int, Animation<AtlasRegion>>) {
        val id = gameObject.id
        val texture = gameObject.textureRef
        val name = texture.name
        var mirrored = false

        if (type.name.startsWith(ObjectType.SURFACES.name)) {
            mirrored = (gameObject as Surface).isMirrored
        }

        if (texture.isAnimated) {  // TODO: allow mirroring
            addToAnimations(atlas, id, name, animations)
        } else {
            addToTextures(atlas, id, name, mirrored, regions)
        }
    }

    private fun addToAnimations(atlas: TextureAtlas, id: Int, textureName: String, animations: MutableMap<Int, Animation<AtlasRegion>>) {
        val atlasRegions = atlas.findRegions(textureName)
        val animation = Animation(FRAME_TIME, atlasRegions, Animation.PlayMode.LOOP)
        animations[id] = animation
    }

    private fun addToTextures(atlas: TextureAtlas, id: Int, textureName: String, mirrored: Boolean, regions: MutableMap<Int, TextureRegion>) {
        // should work on Unix-systems, since it is handled as a name
        val region = atlas.findRegion(textureName)

        if (region == null) {
            LOGGER.log(Level.WARNING, GAME_OBJECT_NOT_FOUND)
            return
        }

        val textureRegion = TextureRegion(region)  // to create a copy
        textureRegion.flip(mirrored, false)
        regions[id] = textureRegion
    }

    fun getTexture(type: ObjectType, id: Int): TextureRegion {
        val name = type.name

        return when {
            name.startsWith(ObjectType.CHARACTERS.name) -> charactersTextureCache.getTextureRegionById(id)
            name.startsWith(ObjectType.ITEMS.name) -> itemsTextureCache.getTextureRegionById(id)
            name.startsWith(ObjectType.OBSTACLES.name) -> obstaclesTextureCache.getTextureRegionById(id)
            name.startsWith(ObjectType.SURFACES.name) -> surfaceTextureCache.getTextureRegionById(id)
            else -> throw IllegalArgumentException(NOT_EXISTENT_TYPE)
        }
    }

    fun getTexture(type: ObjectType, id: Int, elapsedTime: Float): TextureRegion {
        val name = type.name

        return when {
            name.startsWith(ObjectType.CHARACTERS.name) -> charactersTextureCache.getTextureRegionById(id, elapsedTime)
            name.startsWith(ObjectType.ITEMS.name) -> itemsTextureCache.getTextureRegionById(id, elapsedTime)
            name.startsWith(ObjectType.OBSTACLES.name) -> obstaclesTextureCache.getTextureRegionById(id, elapsedTime)
            name.startsWith(ObjectType.SURFACES.name) -> surfaceTextureCache.getTextureRegionById(id, elapsedTime)
            else -> throw IllegalArgumentException(NOT_EXISTENT_TYPE)
        }
    }

    fun addToCharactersTextureCache(gameObject: GameObject) {
        addToObjectTextureCache(gameObject, characterAtlas, charactersTextureCache)
    }

    private fun addToObjectTextureCache(gameObject: GameObject, atlas: TextureAtlas, cache: GameObjectTextureCache) {
        val textureRef = gameObject.textureRef
        val regions = atlas.regions

        val textureFrequency = AtomicInteger()
        regions.forEach { region: AtlasRegion -> if (region.name == textureRef.name) textureFrequency.incrementAndGet() }
        textureRef.isAnimated = textureFrequency.get() > 1

        if (textureRef.isAnimated) {
            addToAnimations(atlas, gameObject.id, textureRef.name, cache.textureAnimations)
        } else {
            addToTextures(atlas, gameObject.id, textureRef.name, false, cache.getTextureRegions())
        }
    }

    fun addToItemsTextureCache(gameObject: GameObject) {
        addToObjectTextureCache(gameObject, itemsAtlas, itemsTextureCache)
    }

    fun addToObstaclesTextureCache(gameObject: GameObject) {
        addToObjectTextureCache(gameObject, obstaclesAtlas, obstaclesTextureCache)
    }

    fun addToSurfacesTextureCache(gameObject: GameObject) {
        addToObjectTextureCache(gameObject, surfacesAtlas, surfaceTextureCache)
    }

    fun getSurfaceTextureCache(): GameObjectTextureCache {
        return surfaceTextureCache
    }

    fun setSurfaceTextureCache(surfaceTextureCache: GameObjectTextureCache) {
        this.surfaceTextureCache.setTextureRegions(surfaceTextureCache.getTextureRegions())
    }
}