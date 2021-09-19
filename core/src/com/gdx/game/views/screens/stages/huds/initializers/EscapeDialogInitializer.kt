package com.gdx.game.views.screens.stages.huds.initializers

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.Linker
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.dialogs.EscapeDialog
import com.gdx.game.views.screens.stages.huds.EditorHud

class EscapeDialogInitializer private constructor() {
    companion object {
        val instance: EscapeDialogInitializer = EscapeDialogInitializer()

        private fun linkEscapeDialogButtons(parent: EditorHud, btnSaveQuit: Button, btnQuit: Button) {
            btnSaveQuit.addListener(object : ChangeListener() {

                override fun changed(event: ChangeEvent, actor: Actor) {
                    parent.escapeDialog?.isVisible = false
                    parent.saveQuitDialog?.isVisible = true
                }
            })

            Linker.linkGameExitButton(btnQuit)
        }
    }

    fun createDialog(skin: Skin, parent: EditorHud): Dialog {
        val escapeDialog = EscapeDialog(parent, "Save & Quit", "Do you want to save your changes?", skin)

        // define
        val btnSaveQuit = TextButton("Save & Quit", skin, Names.Button.BUTTON)
        val btnQuit = TextButton("Quit", skin, Names.Button.BUTTON)

        // link
        linkEscapeDialogButtons(parent, btnSaveQuit, btnQuit)

        // add
        escapeDialog.addButton(btnSaveQuit)
        escapeDialog.addButton(btnQuit)
        return escapeDialog
    }
}