package com.gdx.game.views.screens.stages.dialogs

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.Adjuster
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.widgets.ActorFloating
import com.gdx.game.views.screens.widgets.Paddings

class SaveDialog(parent: Stage, title: String, message: String, textFieldValue: String, skin: Skin) : Dialog(parent, title, skin) {
    val textField: TextField

    init {
        // create
        val messageLabel = Label(message, skin)
        textField = TextField(textFieldValue, skin, Names.INPUT_FIELD)

        // adjust
        Proportioner.setFontSize(messageLabel, 0.000521f)
        Adjuster.adjustTextFieldSpaces(textField.style.background, 0.0208f, true)
        keyboardFocus = textField

        // add
        TableAdder.addTo(window, messageLabel, Paddings(0.0694f, 0f, 0f, 0f), arrayOf(ActorFloating.CENTER), 2, true)
        TableAdder.addTo(window, textField, Vector2(0.139f, 0.0417f), 2, true)
        TableAdder.addRowTo(window, 0.0556f, 2, true)
    }

    fun setTextFieldText(text: String) {
        textField.text = text
    }
}