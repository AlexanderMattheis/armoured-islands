package com.gdx.game.system.storage.mapping.textures

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gdx.game.system.defaults.messages.errors.Errors.NO_SUCH_ELEMENT

class GameObjectTextureCache(private val textureRegions: MutableMap<Int, TextureRegion>,
                             val textureAnimations: MutableMap<Int, Animation<AtlasRegion>>) {

    fun getTextureRegionById(id: Int): TextureRegion {
        return textureRegions[id] ?: throw NoSuchElementException(NO_SUCH_ELEMENT)
    }

    fun getTextureRegionById(id: Int, elapsedTime: Float): TextureRegion {
        return textureAnimations[id]?.getKeyFrame(elapsedTime) ?: throw NoSuchElementException(NO_SUCH_ELEMENT)
    }

    fun getTextureRegions(): MutableMap<Int, TextureRegion> {
        return textureRegions
    }

    fun setTextureRegions(textureRegions: Map<Int, TextureRegion>) {
        this.textureRegions.clear()
        this.textureRegions.putAll(textureRegions)
    }
}