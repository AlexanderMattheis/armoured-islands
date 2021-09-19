package com.gdx.game.system.defaults.views.world.objects

import com.badlogic.gdx.math.Vector2
import com.gdx.game.views.world.objects.elements.actions.Action
import com.gdx.game.views.world.objects.elements.actions.ActionType
import com.gdx.game.views.world.objects.elements.actions.ActionVariable
import com.gdx.game.views.world.objects.elements.actions.Movement

object ObstacleDefaults {
    const val ANIMATED = false
    const val BLOCKED = true
    const val INDEX = 0
    const val ROTATION = 0

    val ACTION_TYPE = ActionType.NONE
    val ACTION_VARIABLE = ActionVariable.NONE
    val ACTION = Action(ACTION_TYPE, ACTION_VARIABLE, 1)
    val MOVEMENT = Movement.NONE
    val POSITION = Vector2.Zero
}