package com.gdx.game.views.world.objects.types

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.views.world.objects.SurfaceDefaults
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.elements.references.SoundReference
import com.gdx.game.views.world.objects.elements.references.TextureReference

class Surface(textureRef: TextureReference, soundRef: SoundReference?, position: Vector2, rotation: Int, mirrored: Boolean)
    : GameObject(textureRef, soundRef, position, rotation, SurfaceDefaults.MOVEMENT, SurfaceDefaults.ACTION) {
    var isBlocked: Boolean = SurfaceDefaults.BLOCKED
    var isMirrored: Boolean = mirrored
}