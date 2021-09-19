package com.gdx.game.views.screens.compositing.adder

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.gdx.game.Main.Companion.windowHeightPixel
import com.gdx.game.views.screens.widgets.ActorFloating
import com.gdx.game.views.screens.widgets.Paddings

object TableAdder : Adder() {
    fun addTo(table: Table, actor: Actor) {
        table.add(actor)
    }

    fun addTo(table: Table, actor: Actor, dimensions: Vector2, colSpan: Int, newRow: Boolean) {
        table.add(actor)
                .width(dimensions.x * windowHeightPixel)
                .height(dimensions.y * windowHeightPixel)
                .colspan(colSpan)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, newRow: Boolean) {
        table.add(actor)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, colSpan: Int, newRow: Boolean) {
        table.add(actor).colspan(colSpan)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings, floatings: Array<ActorFloating>) {
        table.add(actor)
                .padLeft(paddings.left * windowHeightPixel)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)

        addFloating(table, floatings)
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings, floatings: Array<ActorFloating>, newRow: Boolean) {
        table.add(actor)
                .padLeft(paddings.left * windowHeightPixel)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)

        addFloating(table, floatings)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, floatings: Array<ActorFloating>) {
        table.add(actor)
        addFloating(table, floatings)
    }

    fun addTo(table: Table, actor: Actor, floatings: Array<ActorFloating>, newRow: Boolean) {
        table.add(actor)
        addFloating(table, floatings)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings, floatings: Array<ActorFloating>, colSpan: Int, newRow: Boolean) {
        table.add(actor)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)
                .colspan(colSpan)

        addFloating(table, floatings)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, dimensions: Vector2, newRow: Boolean) {
        table.add(actor)
                .width(dimensions.x * windowHeightPixel)
                .height(dimensions.y * windowHeightPixel)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, dimensions: Vector2, paddings: Paddings) {
        table.add(actor)
                .width(dimensions.x * windowHeightPixel)
                .height(dimensions.y * windowHeightPixel)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)
    }

    fun addTo(table: Table, actor: Actor, size: Float, paddings: Paddings) {
        table.add(actor)
                .width(size * windowHeightPixel)
                .height(size * windowHeightPixel)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)
    }

    fun addTo(table: Table, actor: Actor, size: Float) {
        table.add(actor)
                .width(size * windowHeightPixel)
                .height(size * windowHeightPixel)
    }

    fun addTo(table: Table, actor: Actor, dimensions: Vector2, paddings: Paddings, newRow: Boolean) {
        table.add(actor)
                .width(dimensions.x * windowHeightPixel)
                .height(dimensions.y * windowHeightPixel)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, dimensions: Vector2) {
        table.add(actor)
                .width(dimensions.x * windowHeightPixel)
                .height(dimensions.y * windowHeightPixel)
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings) {
        table.add(actor)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings, colSpan: Int) {
        table.add(actor)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)
                .colspan(colSpan)
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings, colSpan: Int, newRow: Boolean) {
        table.add(actor)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)
                .colspan(colSpan)

        if (newRow) {
            table.row()
        }
    }

    fun addTo(table: Table, actor: Actor, paddings: Paddings, newRow: Boolean) {
        table.add(actor)
                .padTop(paddings.top * windowHeightPixel)
                .padRight(paddings.right * windowHeightPixel)
                .padBottom(paddings.bottom * windowHeightPixel)
                .padLeft(paddings.left * windowHeightPixel)

        if (newRow) {
            table.row()
        }
    }

    fun addRowTo(table: Table, height: Float, colSpan: Int) {
        table.add().height(height * windowHeightPixel).colspan(colSpan).row()
    }

    fun addRowTo(table: Table, height: Float, colSpan: Int, fill: Boolean) {
        if (fill) {
            table.add().height(height * windowHeightPixel).colspan(colSpan).fill().row()
        } else {
            table.add().height(height * windowHeightPixel).colspan(colSpan).row()
        }
    }

    fun addRowTo(table: Table) {
        table.add().row()
    }

    fun addScrollButtonsTo(table: Table, buttons: Actor, width: Float, height: Float,
                           paddingRight: Float, paddingBottom: Float, newRow: Boolean) {
        table.add(buttons)
                .width(width * windowHeightPixel)
                .height(height * windowHeightPixel)
                .right()
                .padRight(paddingRight * windowHeightPixel)
                .padBottom(paddingBottom * windowHeightPixel)

        if (newRow) {
            table.row()
        }
    }

    fun addScrollPanelTo(window: Window, panel: Actor, width: Float, height: Float) {
        window.add(panel).width(width * windowHeightPixel).height(height * windowHeightPixel).row()
    }
}