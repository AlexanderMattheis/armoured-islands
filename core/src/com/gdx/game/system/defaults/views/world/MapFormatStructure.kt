package com.gdx.game.system.defaults.views.world

object MapFormatStructure {
    object Tags {
        const val MAP = "Map"
        const val OBJECTS = "Objects"
        const val OBJECT = "Object"
    }

    object MapTagAttributes {
        const val HEIGHT = "height"
        const val NAME = "name"
        const val WIDTH = "width"
    }

    object ObjectsTagAttributes {
        const val TYPE = "type"
    }

    object ObjectTagAttributes {
        const val ACTION_TYPE = "actionType"
        const val ACTION_VARIABLE = "variable"
        const val ANIMATED = "animated"
        const val BLOCKED = "blocked"
        const val DRAGGABLE = "draggable"
        const val INDEX = "index"
        const val LIVES = "lives"
        const val POINTS = "points"
        const val MIRRORED = "mirrored"
        const val MOVEMENT = "movement"
        const val NAME = "name"
        const val PLAYABLE = "playable"
        const val ROTATION = "rotation"
        const val TIMES = "times"
        const val TO_X = "toX"
        const val TO_Y = "toY"
        const val X = "x"
        const val Y = "y"
    }
}