package com.gdx.game.views.screens.stages.dialogs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.gdx.game.system.defaults.views.screens.widgets.Constraints.MAX_NUMBER_DIALOG_BUTTONS
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions.Button.MENU
import com.gdx.game.system.defaults.views.screens.widgets.Names.Button.EXIT
import com.gdx.game.system.defaults.views.screens.widgets.Names.DIALOG_WINDOW
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Constraints.MAX_NUMBER_DIALOG_CHECKBOXES
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions
import com.gdx.game.system.defaults.views.screens.widgets.Positions
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.StageAdder
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.MenuFloating
import com.gdx.game.views.screens.widgets.Paddings

open class Dialog constructor(parent: Stage, title: String, skin: Skin) : Stage() {
    private var numberOfButtons = 0
    private var numberOfCheckBoxes = 0

    val window: Window = Window(title, skin, DIALOG_WINDOW)
    private val parent: Stage = parent

    // to take input from ui
    var isVisible: Boolean
        get() = window.isVisible
        set(visible) {
            window.isVisible = visible

            if (visible) {
                Gdx.input.inputProcessor = this // to take input from ui
            } else {
                Gdx.input.inputProcessor = parent
            }
        }

    // TODO: Add possibility to use the "backwards"-key to switch between dialogs (stack of dialogs)
    init {
        // define
        val dialogWidth = 0.7865f
        val dialogHeight = 0.3785f
        val dialogPosition = Positions.DIALOG
        val dialogDimension = Vector2(dialogWidth, dialogHeight)

        // create
        val btnExit = Button(skin, EXIT)

        // link
        linkExitButton(btnExit)
        linkScreenActions()

        // adjust
        Proportioner.setPadding(window, Paddings(0.0556f, 0.0278f, 0.0347f, 0.0278f))
        window.isMovable = false

        // add
        TableAdder.addTo(window.titleTable, btnExit, 0.0278f)
        StageAdder.addTo(window, this, dialogPosition, dialogDimension, Align.center, MenuFloating.CENTER)

        this.isVisible = false
    }

    fun addButton(button: Button) {
        if (numberOfButtons >= MAX_NUMBER_DIALOG_BUTTONS) {
            throw IndexOutOfBoundsException(Errors.TOO_MANY_DIALOG_BUTTONS)
        }

        if (numberOfButtons == 0) {
            TableAdder.addTo(window, button, MENU, Paddings(0f, 0.01389f, 0f, 0f))
        } else if (numberOfButtons == 1) {
            TableAdder.addTo(window, button, MENU, Paddings(0f, 0f, 0f, 0.01389f))
        }

        numberOfButtons++
    }

    fun addCheckbox(checkBox: CheckBox) {
        if (numberOfCheckBoxes >= MAX_NUMBER_DIALOG_CHECKBOXES) {
            throw IndexOutOfBoundsException(Errors.TOO_MANY_CHECKBOXES)
        }

        TableAdder.addTo(window, checkBox, Dimensions.CHECK_BOX, Paddings(0f, 0f, 0f, 0f))

        numberOfCheckBoxes++
    }

    private fun linkExitButton(exitButton: Button) {
        val thatDialog = this

        exitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                thatDialog.isVisible = false
            }
        })
    }

    private fun linkScreenActions() {
        val thatDialog = this

        this.root.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    thatDialog.isVisible = !thatDialog.isVisible
                }
                return true
            }
        })
    }
}