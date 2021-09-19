package com.gdx.game.views.screens.widgets.groups

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.widgets.Paddings

class HorizontalFilterGroup(heading: String, private val startPadding: Float, skin: Skin) : Table() {
    private var numberOfButtons = 0
    private val filterNames: ButtonGroup<Button> = ButtonGroup()

    init {
        val filter = Label(heading, skin)
        TableAdder.addTo(this, filter, Paddings(0f, startPadding, 0f, 0f))
    }

    fun addButton(button: Button, padding: Float) {
        filterNames.add(button)

        if (numberOfButtons == 0) {
            TableAdder.addTo(this, button, Paddings(0f, padding, 0f, startPadding))
        } else {
            TableAdder.addTo(this, button, Paddings(0f, padding, 0f, padding))
        }

        numberOfButtons++
    }
}