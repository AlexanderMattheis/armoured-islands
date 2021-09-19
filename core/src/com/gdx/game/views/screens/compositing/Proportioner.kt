package com.gdx.game.views.screens.compositing

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.gdx.game.Main.Companion.leftMarginPercent
import com.gdx.game.Main.Companion.windowHeightPixel
import com.gdx.game.views.screens.stages.MenuFloating
import com.gdx.game.views.screens.widgets.Paddings

object Proportioner {
    fun setDimensions(actors: Array<Actor>, dimensions: Vector2) {
        for (actor in actors) {
            setDimension(actor, dimensions)
        }
    }

    fun setDimension(actor: Actor, dimensions: Vector2) {
        actor.width = dimensions.x * windowHeightPixel
        actor.height = dimensions.y * windowHeightPixel
    }

    fun setPositions(actors: Array<Actor>, startPosition: Vector2, spaces: Vector2, alignment: Int, menuFloating: MenuFloating) {
        for (i in actors.indices) {
            setPosition(actors[i], Vector2(startPosition.x + i * spaces.x, startPosition.y - i * spaces.y), alignment, menuFloating)
        }
    }

    fun setPosition(actor: Actor, percentPos: Vector2, menuFloating: MenuFloating) {
        when (menuFloating) {
            MenuFloating.LEFT -> actor.setPosition(percentPos.x * windowHeightPixel, percentPos.y * windowHeightPixel)
            MenuFloating.CENTER -> actor.setPosition((percentPos.x + leftMarginPercent) * windowHeightPixel, percentPos.y * windowHeightPixel)
            MenuFloating.RIGHT -> actor.setPosition((percentPos.x + 2 * leftMarginPercent) * windowHeightPixel, percentPos.y * windowHeightPixel)
        }
    }

    fun setPosition(actor: Actor, percentPos: Vector2, alignment: Int, menuFloating: MenuFloating) {
        when (menuFloating) {
            MenuFloating.LEFT -> actor.setPosition(percentPos.x * windowHeightPixel, percentPos.y * windowHeightPixel, alignment)
            MenuFloating.CENTER -> actor.setPosition((percentPos.x + leftMarginPercent) * windowHeightPixel, percentPos.y * windowHeightPixel, alignment)
            MenuFloating.RIGHT -> actor.setPosition((percentPos.x + 2 * leftMarginPercent) * windowHeightPixel, percentPos.y * windowHeightPixel, alignment)
        }
    }

    fun setPadding(table: Table, paddings: Paddings) {
        table.pad(paddings.top * windowHeightPixel, paddings.left * windowHeightPixel, paddings.bottom * windowHeightPixel, paddings.right * windowHeightPixel)
    }

    fun setFontSizes(buttons: Array<ImageTextButton>, fontSize: Float) {
        for (button in buttons) {
            button.label.setFontScale(fontSize * windowHeightPixel)
        }
    }

    fun setFontSize(skin: Skin, fontName: String?, fontSize: Float) {
        skin.getFont(fontName).data.setScale(fontSize * windowHeightPixel)
        skin.getFont(fontName).setUseIntegerPositions(false)
    }

    fun setFontSize(label: Label, fontSize: Float) {
        label.setFontScale(fontSize * windowHeightPixel)
    }

    fun setScrollBarSizes(scrollPane: ScrollPane, scrollbarSize: Vector2) {
        val vScrollKnob = scrollPane.style.vScrollKnob
        vScrollKnob.minWidth = scrollbarSize.x * windowHeightPixel
        vScrollKnob.minHeight = scrollbarSize.y * windowHeightPixel
    }
}