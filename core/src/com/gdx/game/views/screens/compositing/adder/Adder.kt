package com.gdx.game.views.screens.compositing.adder

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.gdx.game.views.screens.widgets.ActorFloating

open class Adder {
    companion object {

        fun addFloating(table: Table, floatings: Array<ActorFloating>) {
            for (floating in floatings) {
                when (floating) {
                    ActorFloating.BOTTOM -> table.bottom()
                    ActorFloating.CENTER -> table.center()
                    ActorFloating.LEFT -> table.left()
                    ActorFloating.RIGHT -> table.right()
                    ActorFloating.TOP -> table.top()
                }
            }
        }
    }
}