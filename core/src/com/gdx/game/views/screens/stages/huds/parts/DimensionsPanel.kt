package com.gdx.game.views.screens.stages.huds.parts

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.gdx.game.Main.Companion.manager
import com.gdx.game.system.defaults.Regex.POSITIVE_INTEGER
import com.gdx.game.system.defaults.views.world.MapArchiveDefaults
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.huds.EditorHud
import com.gdx.game.views.screens.widgets.Paddings
import com.gdx.game.views.screens.widgets.fields.NumberField
import com.gdx.game.views.world.editor.MapEditor

class DimensionsPanel(skin: Skin, parent: EditorHud) : Table() {

    init {
        val lblWidth = Label("Width", skin)
        val lblHeight = Label("Height", skin)

        val mapDimensions = parent.map.getDimensions()

        val txtWidth = NumberField(mapDimensions.x.toInt(), MapArchiveDefaults.MAP_MIN_SIZE, MapArchiveDefaults.MAP_MAX_SIZE, parent.editorControls, skin)
        val txtHeight = NumberField(mapDimensions.y.toInt(), MapArchiveDefaults.MAP_MIN_SIZE, MapArchiveDefaults.MAP_MAX_SIZE, parent.editorControls, skin)

        // link
        linkDimensionsPanel(parent, txtWidth, txtHeight)

        // add
        TableAdder.addRowTo(this, 0.205f, 4)
        TableAdder.addTo(this, lblWidth, Vector2(0.0573f, 0.0191f), Paddings(0f, 0f, 0f, 0.01042f))
        TableAdder.addTo(this, txtWidth, Vector2(0.139f, 0.0434f), Paddings(0f, 0.00868f, 0f, 0f))
        TableAdder.addTo(this, lblHeight, Vector2(0.0573f, 0.0191f), Paddings(0f, 0f, 0f, 0.00868f))
        TableAdder.addTo(this, txtHeight, Vector2(0.139f, 0.0434f))
    }

    private fun linkDimensionsPanel(parent: EditorHud, txtWidth: NumberField, txtHeight: NumberField) {
        val changeListener: ChangeListener = object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                createMap(parent.editor, txtWidth.text, txtHeight.text)
            }
        }

        val clickListener: ClickListener = object : ClickListener() {
            // ok, since these buttons are never get disabled
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                createMap(parent.editor, txtWidth.text, txtHeight.text)
            }
        }

        txtWidth.addListener(changeListener)
        txtWidth.buttonDown.addListener(clickListener)
        txtWidth.buttonUp.addListener(clickListener)

        txtHeight.addListener(changeListener)
        txtHeight.buttonDown.addListener(clickListener)
        txtHeight.buttonUp.addListener(clickListener)
    }

    private fun createMap(editor: MapEditor, width: String, height: String) {
        if (width.matches(POSITIVE_INTEGER) && height.matches(POSITIVE_INTEGER)) {
            val newMap = editor.createEmptyMap(width.toInt(), height.toInt())

            editor.map.clear()
            editor.map.setDimensions(newMap.getDimensions())
            editor.map.setSurfaces(newMap.getSurfaces())
            editor.cameraController.setMapDimensions(newMap.getDimensions())

            val newTexturesCache = MapTexturesCache(manager, editor.map)
            editor.texturesCache.setSurfaceTextureCache(newTexturesCache.getSurfaceTextureCache())
        }
    }
}