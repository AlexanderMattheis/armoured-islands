package com.gdx.game.views.world.objects.elements.actions

import com.gdx.game.system.defaults.messages.errors.Errors.NOT_EXISTENT_TYPE
import java.io.Serializable

enum class Movement(val type: String) : Serializable {
    DIAGONAL("diagonal"),
    DIRECTIONS_4("directions-4"),
    DIRECTIONS_8("directions-8"),
    FREE("free"),
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    NONE("");

    companion object {
        fun getTypeByName(name: String): Movement {
            return when (name) {
                DIAGONAL.type -> DIAGONAL
                DIRECTIONS_4.type -> DIRECTIONS_4
                DIRECTIONS_8.type -> DIRECTIONS_8
                FREE.type -> FREE
                HORIZONTAL.type -> HORIZONTAL
                VERTICAL.type -> VERTICAL
                NONE.type -> NONE
                else -> throw IllegalArgumentException(NOT_EXISTENT_TYPE)
            }
        }
    }

}