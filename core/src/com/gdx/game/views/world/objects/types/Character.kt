package com.gdx.game.views.world.objects.types

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.views.world.objects.CharacterDefaults
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.elements.actions.Movement
import com.gdx.game.views.world.objects.elements.references.SoundReference
import com.gdx.game.views.world.objects.elements.references.TextureReference

class Character(textureRef: TextureReference, soundRef: SoundReference?, position: Vector2, rotation: Int, movement: Movement)
    : GameObject(textureRef, soundRef, position, rotation, movement, CharacterDefaults.ACTION) {

    var isPlayable: Boolean = CharacterDefaults.PLAYABLE
    var lives: Int = CharacterDefaults.LIVES
    var points: Int = CharacterDefaults.POINTS
    var maxLives: Int = CharacterDefaults.LIVES
}