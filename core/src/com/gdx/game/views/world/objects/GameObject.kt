package com.gdx.game.views.world.objects

import com.badlogic.gdx.math.Vector2
import com.gdx.game.views.world.objects.elements.actions.Action
import com.gdx.game.views.world.objects.elements.actions.Movement
import com.gdx.game.views.world.objects.elements.references.SoundReference
import com.gdx.game.views.world.objects.elements.references.TextureReference
import java.io.Serializable

open class GameObject protected constructor(textureRef: TextureReference, soundRef: SoundReference?, position: Vector2,
                                            rotation: Int, movement: Movement, action: Action) : Serializable {
    companion object {
        private var created = 0
    }

    val id: Int
    var textureRef: TextureReference
    var soundRef: SoundReference?
    var position: Vector2
    var rotation: Int
    var movement: Movement
    var action: Action

    init {
        id = created
        this.textureRef = textureRef
        this.soundRef = soundRef
        this.position = position
        this.rotation = rotation
        this.movement = movement
        this.action = action

        created++
    }
}