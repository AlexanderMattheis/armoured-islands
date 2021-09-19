package com.gdx.game.views.screens.stages.huds.initializers

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.gdx.game.Main
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.system.storage.mapping.MapZipArchiveSaver
import com.gdx.game.views.screens.MenuScreen
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.dialogs.SaveDialog
import com.gdx.game.views.screens.stages.huds.EditorHud

class SaveDialogInitializer private constructor() {
    companion object {
        val instance: SaveDialogInitializer = SaveDialogInitializer()
    }

    fun createDialog(skin: Skin, parent: EditorHud, saveAndQuit: Boolean = false): Dialog {
        val saveDialog = SaveDialog(parent, "Save", "How do you want to name your map?", "", skin)

        // define
        //val chbTakeScreenshot = CheckBox("Take Screenshot", skin)
        val btnSave = TextButton("Save", skin, Names.Button.BUTTON)
        val btnCancel = TextButton("Cancel", skin, Names.Button.BUTTON)

        // link
        linkSaveDialog(parent, saveDialog, btnSave, btnCancel, saveAndQuit)

        // add
        saveDialog.addButton(btnSave)
        saveDialog.addButton(btnCancel)
        return saveDialog
    }

    private fun linkSaveDialog(parent: EditorHud, saveDialog: SaveDialog, btnSave: Button, btnCancel: Button, saveAndQuit: Boolean) {
        saveDialog.textField.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                parent.map.name = saveDialog.textField.text  // updates the name in map
            }
        })

        btnSave.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                // TODO: check if `thatHud.map` already exists and if `yes` then open new dialog "Are you sure, you want override .."
                MapZipArchiveSaver.save(Paths.User.Folders.MAPS, parent.map)

                if (saveAndQuit) {
                    Main.instance.screen = MenuScreen.getInstance()
                } else {
                    saveDialog.isVisible = false
                }
            }
        })

        btnCancel.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                saveDialog.isVisible = false

                if (saveAndQuit) {
                    parent.escapeDialog?.isVisible = true
                }
            }
        })
    }
}