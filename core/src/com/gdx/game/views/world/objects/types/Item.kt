package com.gdx.game.views.world.objects.types

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.views.world.objects.ItemDefaults
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.elements.references.SoundReference
import com.gdx.game.views.world.objects.elements.references.TextureReference

class Item(textureRef: TextureReference, soundRef: SoundReference?, position: Vector2)
    : GameObject(textureRef, soundRef, position, ItemDefaults.ROTATION, ItemDefaults.MOVEMENT, ItemDefaults.ACTION) {

    var isDraggable: Boolean = ItemDefaults.DRAGGABLE
}