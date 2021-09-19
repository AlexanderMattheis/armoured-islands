package com.gdx.game.views.screens.widgets.fields

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener
import com.badlogic.gdx.utils.Align
import com.gdx.game.system.defaults.Regex
import com.gdx.game.system.defaults.Regex.NUMBER
import com.gdx.game.system.defaults.Regex.ZEROS
import com.gdx.game.system.defaults.views.screens.widgets.Texts
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.system.defaults.views.screens.widgets.Names.Button.NumberInputButton.NUMBER_INPUT_BUTTON_DOWN
import com.gdx.game.system.defaults.views.screens.widgets.Names.Button.NumberInputButton.NUMBER_INPUT_BUTTON_UP
import com.gdx.game.system.defaults.views.screens.widgets.Positions
import com.gdx.game.system.settings.config.shortcuts.EditorControls
import com.gdx.game.views.screens.compositing.Adjuster
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.widgets.Paddings
import com.gdx.game.views.screens.widgets.Widgets

class NumberField(defaultValue: Int, minValue: Int, maxValue: Int,
                  editorControls: EditorControls, skin: Skin?) : Table() {

    private val textField: TextField = TextField(defaultValue.toString(), skin, Names.INPUT_FIELD)

    val buttonUp: Button = Button(skin, NUMBER_INPUT_BUTTON_UP)
    val buttonDown: Button = Button(skin, NUMBER_INPUT_BUTTON_DOWN)

    val text: String
        get() = textField.text

    init {
        // create
        val buttonTable = Table()
        TableAdder.addTo(buttonTable, buttonUp, Vector2(0.0243f, 0.023f), Paddings(0f, 0f, -0.00174f, 0f), true)
        TableAdder.addTo(buttonTable, buttonDown, Vector2(0.0243f, 0.023f), Paddings(-0.0046f, 0f, 0f, 0f))

        // link
        toNumberInputField(textField, buttonUp, buttonDown, minValue, maxValue, editorControls)

        // add
        TableAdder.addTo(this, textField, Vector2(0.1163f, 0.0434f), Paddings(0f, -0.0295f, 0f, 0f))
        TableAdder.addTo(this, buttonTable, Paddings(0f, 0f, 0f, 0.0226f))
    }

    private fun toNumberInputField(txtField: TextField, btnUp: Button, btnDown: Button,
                                   minValue: Int, maxValue: Int, editorControls: EditorControls) {
        txtField.alignment = Align.right

        val txtStyle = txtField.style
        Adjuster.adjustTextFieldSpaces(txtStyle.background, 0.0138f, false)
        Adjuster.adjustTextFieldSpaces(txtStyle.focusedBackground, 0.0138f, false)

        txtField.textFieldFilter = TextField.TextFieldFilter { _: TextField?, c: Char -> c.toString().matches(NUMBER) }

        txtField.addListener(object : FocusListener() {
            override fun keyboardFocusChanged(event: FocusEvent, actor: Actor, focused: Boolean) {
                Widgets.areFocused = focused
            }
        })

        txtField.setTextFieldListener { textField: TextField, _: Char ->
            val inputText = textField.text

            if (inputText.isEmpty()) {
                textField.text = Texts.NUMBER_FIELD
                textField.cursorPosition = Positions.CURSOR
            } else {
                textField.text = inputText.replaceFirst(ZEROS, "")
            }

            if (inputText.isEmpty()) {
                return@setTextFieldListener
            }

            val inputValue = inputText.toInt()  // ok, because you can't write non-numbers in a number-field

            if (inputValue < minValue) {
                textField.text = minValue.toString()
            }

            if (inputValue > maxValue) {
                textField.text = maxValue.toString()
            }
        }

        btnUp.addListener(object : ClickListener() {
            // ok, since it gets never disabled
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                setText(editorControls, txtField, minValue, maxValue, maxValue, Int::plus)
            }
        })

        btnDown.addListener(object : ClickListener() {
            // ok, since it gets never disabled
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                setText(editorControls, txtField, minValue, maxValue, minValue, Int::minus)
            }
        })
    }

    private fun setText(editorControls: EditorControls, txtField: TextField,
                        minValue: Int, maxValue: Int, defaultValue: Int, op: (Int, Int) -> Int) {
        var shift = 1

        if (Gdx.input.isKeyPressed(editorControls.keyCodeChangeValueByFactor10)) {
            shift = 10
        }

        if (!txtField.text.matches(Regex.POSITIVE_INTEGER)) {
            txtField.text = defaultValue.toString()
            return
        }

        val inputValue = txtField.text.toInt()  // ok, because you can't write non-numbers

        if (op(inputValue, shift) in minValue..maxValue) {
            txtField.text = op(inputValue, shift).toString()
        } else {
            txtField.text = defaultValue.toString()
        }
    }

    override fun addListener(listener: EventListener): Boolean {
        textField.addListener(listener)
        return true
    }
}