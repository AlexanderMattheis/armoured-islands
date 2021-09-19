package com.gdx.game.views.world.editor

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.views.world.EditorDefaults
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.dialogs.SaveDialog
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.management.ObjectCreator
import com.gdx.game.views.world.editor.management.ObjectManager
import com.gdx.game.views.world.editor.management.StateManager
import com.gdx.game.views.world.editor.management.tracking.MapChange
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import com.gdx.game.views.world.objects.elements.references.TextureReference
import com.gdx.game.views.world.objects.types.Surface

class MapEditor(mapToLoad: Map?, manager: AssetManager, settings: Settings,
                var selectedType: ObjectType, var objectName: String, private val defaultSurfaceName: String) {

    val map: Map = mapToLoad ?: createEmptyMap(EditorDefaults.MAP_WIDTH, EditorDefaults.MAP_HEIGHT)
    val texturesCache: MapTexturesCache = MapTexturesCache(manager, map)
    val cameraController: CameraController = CameraController(settings, map)

    private val stateManager: StateManager = StateManager(map, cameraController)
    private val objectCreator: ObjectCreator = ObjectCreator(texturesCache)
    private val objectManager: ObjectManager = ObjectManager(map, texturesCache)

    var isObjectPlaceable = false
    var selectedObject: GameObject? = null
    var hoveredObject: GameObject? = null

    val isMapEdited: Boolean
        get() = stateManager.isMapEdited

    val isMapReedited: Boolean
        get() = stateManager.isMapReedited

    val objectOfSelectedType: GameObject
        get() = objectCreator.create(selectedType, objectName)

    // region STATE_MANAGEMENT
    fun capturePossibleChanges(change: MapChange?) {
        stateManager.capturePossibleChanges(change)
    }

    fun exit(escapeDialog: Dialog) {
        stateManager.exit(escapeDialog)
    }

    fun undo() {
        selectedObject = stateManager.undo()
    }

    fun redo() {
        selectedObject = stateManager.redo()
    }

    fun save(saveDialog: SaveDialog) {
        stateManager.save(saveDialog)
    }

    fun emptyRedoList() {
        stateManager.emptyRedoList()
    }
    // endregion STATE_MANAGEMENT

    // region OBJECT_MANAGEMENT
    fun setSelectedTypeAndObject(selectedType: ObjectType, objectName: String) {
        this.selectedType = selectedType
        this.objectName = objectName
    }

    fun mirrorSelectedObject() {
        objectManager.mirrorObject(selectedObject)
    }

    fun rotateSelectedObject() {
        objectManager.rotateObject(selectedObject)
    }
    // endregion

    fun createEmptyMap(width: Int, height: Int): Map {
        val newMap = Map(EditorDefaults.EMPTY_MAP_NAME, Vector2(width.toFloat(), height.toFloat()))

        for (y in 0 until height) {
            for (x in 0 until width) {
                val position = Vector2(x.toFloat(), y.toFloat())
                val surface = Surface(TextureReference(defaultSurfaceName, 0, false), null, position, 0, false)
                newMap.addSurface(surface)
            }
        }

        return newMap
    }
}