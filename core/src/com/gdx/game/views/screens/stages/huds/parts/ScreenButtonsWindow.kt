package com.gdx.game.views.screens.stages.huds.parts

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.huds.EditorHud
import com.gdx.game.views.screens.widgets.Paddings
import com.gdx.game.views.world.editor.MapEditor

class ScreenButtonsWindow(skin: Skin, parent: EditorHud) : Window("", skin, Names.Panel.TWO_BUTTON) {

    init {
        // create
        val mirrorButton = ImageButton(skin, Names.Button.MIRROR)
        val rotationButton = ImageButton(skin, Names.Button.ROTATION)

        // define
        val buttonSize = 0.0868f

        // link
        linkOnScreenButtonsPanel(parent.editor, mirrorButton, rotationButton)

        // add
        TableAdder.addTo(this, mirrorButton, buttonSize, Paddings(0.00868f, 0.00868f, 0.00868f, 0.00868f))
        TableAdder.addTo(this, rotationButton, buttonSize, Paddings(0.00868f, 0.00868f, 0.00868f, 0.00868f))
    }

    private fun linkOnScreenButtonsPanel(editor: MapEditor, mirrorButton: ImageButton, rotationButton: ImageButton) {
        mirrorButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                editor.mirrorSelectedObject()
            }
        })

        rotationButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                editor.rotateSelectedObject()
            }
        })
    }
}