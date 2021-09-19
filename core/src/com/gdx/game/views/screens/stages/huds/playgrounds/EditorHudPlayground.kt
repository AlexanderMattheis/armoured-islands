package com.gdx.game.views.screens.stages.huds.playgrounds

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.views.screens.stages.huds.EditorHud
import com.gdx.game.views.world.editor.actions.Hover
import com.gdx.game.views.world.editor.actions.Place
import com.gdx.game.views.world.editor.actions.Select

class EditorHudPlayground private constructor() {
    companion object {
        val instance = EditorHudPlayground()
    }

    fun link(editorHud: EditorHud, settings: Settings) {
        val hover = Hover(settings)
        val place = Place(settings)
        val select = Select(settings)
        setListeners(editorHud, hover, place, select)
    }

    private fun setListeners(editorHud: EditorHud, hover: Hover, place: Place, select: Select) {
        val editor = editorHud.editor

        editorHud.root.addListener(object : InputListener() {
            override fun mouseMoved(event: InputEvent, x: Float, y: Float): Boolean {
                val isGuiElementBelow = event.target is Table || event.target is Widget

                if (isGuiElementBelow) {
                    hover.execute(editor.cameraController.cameraPositionPixel, Gdx.input.x, Gdx.input.y, editor.map, editor, null)
                    return false
                }

                hover.execute(editor.cameraController.cameraPositionPixel, Gdx.input.x, Gdx.input.y, editor.map, editor, editor.objectOfSelectedType)
                return true
            }
        })

        editorHud.root.addListener(object : ClickListener() {
            // necessary
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                val isGuiElementBelow = event.target is Table || event.target is Widget

                if (isGuiElementBelow) {
                    return
                }

                if (Gdx.input.isKeyPressed(editorHud.editorControls.keyCodeSelectGameObject)) {
                    select.execute(editor.cameraController.cameraPositionPixel, Gdx.input.x, Gdx.input.y, editor.map, editor)
                } else {
                    if (editor.isObjectPlaceable) { // to capture only where a change is possible
                        editor.capturePossibleChanges(place.execute(editor.cameraController.cameraPositionPixel, Gdx.input.x, Gdx.input.y, editor.map, editor.objectOfSelectedType))
                        editorHud.reactivateChangesHistory()
                        editor.emptyRedoList()
                    }
                    // IMPORTANT: necessary to update the drawn rectangle around a tile
                    hover.execute(editor.cameraController.cameraPositionPixel, Gdx.input.x, Gdx.input.y, editor.map, editor, editor.objectOfSelectedType)
                }
            }
        })
    }
}