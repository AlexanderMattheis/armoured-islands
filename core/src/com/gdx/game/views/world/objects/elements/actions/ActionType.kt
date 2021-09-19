package com.gdx.game.views.world.objects.elements.actions

import com.gdx.game.system.defaults.messages.errors.Errors.NOT_EXISTENT_TYPE
import java.io.Serializable

enum class ActionType(val actionTypeName: String) : Serializable {
    INCREMENT("increment"),
    DECREMENT("decrement"),
    NONE("");

    companion object {
        fun getTypeByName(name: String): ActionType {
            return when (name) {
                INCREMENT.actionTypeName -> INCREMENT
                DECREMENT.actionTypeName -> DECREMENT
                NONE.actionTypeName -> NONE
                else -> throw IllegalArgumentException(NOT_EXISTENT_TYPE)
            }
        }
    }

}