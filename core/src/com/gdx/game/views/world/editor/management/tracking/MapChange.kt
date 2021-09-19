package com.gdx.game.views.world.editor.management.tracking

import com.badlogic.gdx.math.Vector2

// to jump to the position and show which change has been made, the camera position is stored
open class MapChange(val cameraPosition: Vector2, val tilePosition: Vector2)