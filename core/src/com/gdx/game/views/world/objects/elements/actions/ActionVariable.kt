package com.gdx.game.views.world.objects.elements.actions

import com.gdx.game.system.defaults.messages.errors.Errors.NOT_EXISTENT_TYPE
import java.io.Serializable

enum class ActionVariable(val actionVariable: String) : Serializable {
    POINTS("points"),
    LIVES("lives"),
    NONE("");

    companion object {
        fun getTypeByName(name: String): ActionVariable {
            return when (name) {
                POINTS.actionVariable -> POINTS
                LIVES.actionVariable -> LIVES
                NONE.actionVariable -> NONE
                else -> throw IllegalArgumentException(NOT_EXISTENT_TYPE)
            }
        }
    }

}