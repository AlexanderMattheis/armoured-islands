package com.gdx.game.views.screens.stages.dialogs

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.widgets.Paddings

class EscapeDialog(parent: Stage, title: String, message: String, skin: Skin) : Dialog(parent, title, skin) {

    init {
        // create
        val messageLabel = Label(message, skin)

        // adjust
        Proportioner.setFontSize(messageLabel, 0.000521f)

        // add
        TableAdder.addTo(window, messageLabel, Paddings(0.0694f, 0f, 0f, 0f), 2, true)
        TableAdder.addRowTo(window, 0.0972f, 2, false)
    }
}