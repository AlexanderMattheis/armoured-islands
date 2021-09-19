package com.gdx.game.views.world.objects.types

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.views.world.objects.ObstacleDefaults
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.elements.references.SoundReference
import com.gdx.game.views.world.objects.elements.references.TextureReference

class Obstacle(textureRef: TextureReference, soundRef: SoundReference?, position: Vector2, rotation: Int)
    : GameObject(textureRef, soundRef, position, rotation, ObstacleDefaults.MOVEMENT, ObstacleDefaults.ACTION) {

    var isBlocked = true
    var toPosition: Vector2 = Vector2(Float.NaN, Float.NaN)
}