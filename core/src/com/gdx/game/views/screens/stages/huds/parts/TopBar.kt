package com.gdx.game.views.screens.stages.huds.parts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.dialogs.SaveDialog
import com.gdx.game.views.screens.stages.huds.EditorHud
import com.gdx.game.views.screens.widgets.ActorFloating
import com.gdx.game.views.screens.widgets.Paddings
import com.gdx.game.views.world.editor.MapEditor
import java.lang.NullPointerException

class TopBar(skin: Skin, parent: EditorHud) : Table() {
    private val btnExit: Button = Button(skin, Names.Button.IconButton.EXIT)
    val btnUndo: Button = Button(skin, Names.Button.IconButton.HistoryDirection.LEFT)
    val btnRedo: Button = Button(skin, Names.Button.IconButton.HistoryDirection.RIGHT)
    private val btnSave = Button(skin, Names.Button.IconButton.SAVE)

    init {
        // create
        val btnSelect = Button(skin, Names.Button.IconButton.SELECT)
        val btnCut = Button(skin, Names.Button.IconButton.CUT)
        val btnCopy = Button(skin, Names.Button.IconButton.COPY)
        val btnPaste = Button(skin, Names.Button.IconButton.PASTE)
        val btnPin = Button(skin, Names.Button.PIN)
        val tblMain = Table()
        val tblSelectors = Table()

        // define
        val iconButtonDimension = Dimensions.Button.ICON
        val pinDimension = Dimensions.Button.PIN

        // adjust
        btnUndo.isDisabled = true
        btnRedo.isDisabled = true
        btnSelect.isDisabled = true
        btnCut.isDisabled = true
        btnCopy.isDisabled = true
        btnPaste.isDisabled = true
        btnPin.isDisabled = true
        btnPin.touchable = null

        // link
        linkTopBarButton(parent)

        // add
        TableAdder.addTo(tblMain, btnExit, iconButtonDimension)
        TableAdder.addTo(tblMain, btnUndo, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(tblMain, btnRedo, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(tblMain, btnSave, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(tblSelectors, btnSelect, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(tblSelectors, btnCut, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(tblSelectors, btnCopy, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(tblSelectors, btnPaste, iconButtonDimension, Paddings(0f, 0f, 0f, 0.0138f))
        TableAdder.addTo(this, tblMain, arrayOf(ActorFloating.LEFT))
        TableAdder.addTo(this, tblSelectors, Paddings(0f, 0f, 0f, 0.0608f), arrayOf(ActorFloating.RIGHT))
        TableAdder.addTo(this, btnPin, pinDimension)
    }

    private fun linkTopBarButton(parent: EditorHud) {
        val mapEditor = parent.editor
        val escapeDialog = if (parent.escapeDialog != null) parent.escapeDialog!! else throw NullPointerException(Errors.DIALOG_NULL)
        val saveDialog = if (parent.saveDialog != null) parent.saveDialog as SaveDialog else throw NullPointerException(Errors.DIALOG_NULL)

        btnExit.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                mapEditor.exit(escapeDialog)
            }
        })

        // 'undo' and 'redo' are special, because the corresponding buttons have to be enabled/disabled,
        // that is why it is not directly managed by the MapEditor class
        btnUndo.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                manageUndo(mapEditor)
            }
        })

        btnRedo.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                manageRedo(mapEditor)
            }
        })

        btnSave.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                mapEditor.save(saveDialog)
            }
        })

        // shortcuts
        parent.addListener(object : InputListener() {
            // TODO: make controls exchangeable and add right control
            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    mapEditor.exit(escapeDialog)
                } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                        && Gdx.input.isKeyPressed(Input.Keys.Y)) {
                    manageRedo(mapEditor)
                } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.Y)) {
                    manageUndo(mapEditor)
                } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.S)) {
                    mapEditor.save(saveDialog)
                }

                return true
            }
        })
    }

    private fun manageUndo(mapEditor: MapEditor) {
        if (mapEditor.isMapEdited) {  // important when using shortcuts
            mapEditor.undo()
        }

        if (!mapEditor.isMapEdited) {  // check if undo-list still not empty
            btnUndo.isDisabled = true
        }

        btnRedo.isDisabled = false
    }

    private fun manageRedo(mapEditor: MapEditor) {
        if (mapEditor.isMapReedited) {  // important when using shortcuts
            mapEditor.redo()
        }

        if (!mapEditor.isMapReedited) {  // check if redo-list still not empty
            btnRedo.isDisabled = true
        }

        btnUndo.isDisabled = false
    }
}