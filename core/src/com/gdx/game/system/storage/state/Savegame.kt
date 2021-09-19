package com.gdx.game.system.storage.state

import com.badlogic.gdx.math.Vector2
import com.gdx.game.views.world.Map
import java.io.Serializable

data class Savegame(val map: Map, val cameraPosition: Vector2) : Serializable