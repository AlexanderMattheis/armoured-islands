package com.gdx.game.views.screens.compositing

import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener
import com.gdx.game.Main.Companion.windowHeightPixel

object Adjuster {

    fun adjustHorizontalMenu(horizontalPanel: Window, scrollPane: ScrollPane) {
        horizontalPanel.isMovable = false

        horizontalPanel.addListener(object : ClickListener() {
            // HINT: the clickListener is necessary here
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                horizontalPanel.toBack() // to avoid that it gets in front of for example the tab-buttons
                return true
            }
        })

        scrollPane.addListener(object : FocusListener() {
            override fun handle(event: Event): Boolean {
                horizontalPanel.toBack() // to avoid that it gets in front of for example the tab-buttons
                return true
            }
        })
    }

    fun adjustMenuFileButtons(buttons: Array<ImageTextButton>, paddingBottom: Float) {
        for (button in buttons) {
            button.labelCell.padBottom(paddingBottom * windowHeightPixel)
        }
    }

    fun adjustTextFieldSpaces(drawable: Drawable, horizontalSpace: Float, onlyLeft: Boolean) {
        drawable.leftWidth = horizontalSpace * windowHeightPixel

        if (onlyLeft) {
            return
        }

        drawable.rightWidth = horizontalSpace * windowHeightPixel
    }

    fun adjustMenuFileButtonImage(imageTextButton: ImageTextButton, height: Float,
                                  width: Float, paddingTop: Float) {
        imageTextButton.imageCell
                .height(height * windowHeightPixel)
                .width(width * windowHeightPixel)
                .padTop(paddingTop * windowHeightPixel)
    }

    fun enableTextFieldFocusLoss(stage: Stage) {
        stage.root.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (event.target !is TextField) {
                    stage.keyboardFocus = null
                }

                return false
            }
        })
    }
}