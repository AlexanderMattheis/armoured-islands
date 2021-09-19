package com.gdx.game.views.world.editor.management.tracking.changes

import com.badlogic.gdx.math.Vector2
import com.gdx.game.views.world.editor.management.tracking.MapChange
import com.gdx.game.views.world.objects.GameObject

class PlacedObject(cameraPosition: Vector2, tilePosition: Vector2,
                   val beforeObjects: List<GameObject>, val afterObject: GameObject) : MapChange(cameraPosition, tilePosition)