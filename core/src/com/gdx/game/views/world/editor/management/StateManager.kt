package com.gdx.game.views.world.editor.management

import com.gdx.game.Main
import com.gdx.game.views.screens.MenuScreen
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.dialogs.SaveDialog
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.management.tracking.MapChange
import com.gdx.game.views.world.editor.management.tracking.MapChangeList
import com.gdx.game.views.world.editor.management.tracking.changes.PlacedObject
import com.gdx.game.views.world.objects.GameObject

class StateManager(private val map: Map, private val cameraController: CameraController) {
    private val mapChangeHistory: MapChangeList = MapChangeList()
    private var lastMapName: String? = null

    val isMapEdited: Boolean
        get() = mapChangeHistory.hasChanges()

    val isMapReedited: Boolean
        get() = mapChangeHistory.hasRepetitions()

    fun capturePossibleChanges(change: MapChange?) {
        if (change != null) {
            mapChangeHistory.captureChange(change)
        }
    }

    fun exit(escapeDialog: Dialog) {
        if (this.isMapEdited) {
            escapeDialog.isVisible = !escapeDialog.isVisible
        } else {
            Main.instance.screen = MenuScreen.getInstance()
        }
    }

    fun undo(): GameObject? {
        val change = mapChangeHistory.undoChange
        cameraController.cameraPositionPixel = change.cameraPosition

        return if (change is PlacedObject) undoPlacingObject(change) else null
    }

    private fun undoPlacingObject(change: PlacedObject): GameObject {
        val tilePosition = change.tilePosition
        val removedObjects = change.beforeObjects
        val placedObject = change.afterObject

        map.remove(placedObject)
        removedObjects.forEach { gameObject: GameObject -> map.add(gameObject) }
        return map.getSurface(tilePosition)
    }

    fun redo(): GameObject? {
        val change = mapChangeHistory.redoChange
        cameraController.cameraPositionPixel = change.cameraPosition
        return if (change is PlacedObject) redoPlacingObject(change) else null
    }

    private fun redoPlacingObject(change: PlacedObject): GameObject {
        val tilePosition = change.tilePosition
        val removedObjects = change.beforeObjects
        val placedObject = change.afterObject

        removedObjects.forEach { gameObject: GameObject -> map.remove(gameObject) }
        map.add(placedObject)
        return map.getSurface(tilePosition)
    }

    fun save(saveDialog: SaveDialog) {
        val lastMapName = this.lastMapName

        if (lastMapName != null) {
            saveDialog.setTextFieldText(lastMapName)
        }

        saveDialog.isVisible = !saveDialog.isVisible
    }

    fun emptyRedoList() {
        mapChangeHistory.emptyRedoList()
    }
}