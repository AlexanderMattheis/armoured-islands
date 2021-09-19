package com.gdx.game.views.world.objects

import com.gdx.game.system.defaults.messages.errors.Errors.NOT_EXISTENT_TYPE

enum class ObjectType(val objectType: String) {
    CHARACTERS("characters"),
    ITEMS("items"),
    OBSTACLES("obstacles"),
    SURFACES("surfaces"),
    SURFACES_TRANSITIONS("surfaces/transitions");

    companion object {
        fun getTypeByName(name: String): ObjectType {
            return when {
                name.startsWith(CHARACTERS.objectType) -> CHARACTERS
                name.startsWith(ITEMS.objectType) -> ITEMS
                name.startsWith(OBSTACLES.objectType) -> OBSTACLES
                name.startsWith(SURFACES_TRANSITIONS.objectType) -> SURFACES_TRANSITIONS
                name.startsWith(SURFACES.objectType) -> SURFACES
                else -> throw IllegalArgumentException(NOT_EXISTENT_TYPE)
            }
        }
    }

}