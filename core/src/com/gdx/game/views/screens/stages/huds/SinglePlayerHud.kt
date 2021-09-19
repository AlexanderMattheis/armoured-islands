package com.gdx.game.views.screens.stages.huds

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Fonts
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.system.settings.config.shortcuts.EditorControls
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.views.screens.compositing.Adjuster
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.StageAdder
import com.gdx.game.views.screens.stages.MenuFloating
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.huds.parts.BackgroundWindow
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.MapEditor
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class SinglePlayerHud(manager: AssetManager, settings: Settings, map: Map?, skin: Skin) : Hud() {
    companion object {
        private val LOGGER = Logger.getLogger(EditorHud::class.qualifiedName)
    }

    val editorControls: EditorControls
    val editor: MapEditor

    // TODO: create ATLAS-set
    var characterAtlas: TextureAtlas? = null
        private set

    var itemsAtlas: TextureAtlas? = null
        private set

    var obstaclesAtlas: TextureAtlas? = null
        private set

    var surfacesAtlas: TextureAtlas? = null
        private set

    var escapeDialog: Dialog? = null
        private set

    var saveDialog: Dialog? = null
        private set

    var saveQuitDialog: Dialog? = null
        private set

    override val dialogs: Array<Dialog>
        get() {
            val dialogs: MutableList<Dialog?> = ArrayList()
            dialogs.add(escapeDialog)
            dialogs.add(saveDialog)
            dialogs.add(saveQuitDialog)
            return dialogs.filterNotNull().toTypedArray()  // TODO: Use List<T>
        }

    override val selectedObject: GameObject?
        get() {
            return null
        }

    val texturesCache: MapTexturesCache
        get() = editor.texturesCache

    val cameraController: CameraController
        get() = editor.cameraController

    val map: Map
        get() = editor.map

    init {
        init(skin, manager)

        // define
        val controls = settings.controls
        editorControls = controls.editorControls
        editor = MapEditor(map, manager, settings, ObjectType.CHARACTERS, characterAtlas!!.regions[0].name, surfacesAtlas!!.regions[0].name)

        // create
        val window = BackgroundWindow(skin)

        // adjust
        Adjuster.enableTextFieldFocusLoss(this)
        Gdx.input.inputProcessor = this // to take input from ui

        // add
        StageAdder.addTo(this, window, Vector2(0f, 0f), Vector2(0.477f, 1.0f), MenuFloating.LEFT)
    }

    private fun init(skin: Skin, manager: AssetManager) {
        Proportioner.setFontSize(skin, Fonts.INPUT, 0.000521f)
        Proportioner.setFontSize(skin, Fonts.LABEL, 0.000521f)

        // to avoid problems with multiple threads
        val characterAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.CHARACTERS_ATLAS, TextureAtlas::class.java)
        val itemsAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.ITEMS_ATLAS, TextureAtlas::class.java)
        val obstaclesAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.OBSTACLES_ATLAS, TextureAtlas::class.java)
        val surfacesAtlas = manager.get(Paths.SPRITES + Paths.Files.Atlases.SURFACES_ATLAS, TextureAtlas::class.java)

        this.characterAtlas = characterAtlas
        this.itemsAtlas = itemsAtlas
        this.obstaclesAtlas = obstaclesAtlas
        this.surfacesAtlas = surfacesAtlas

        if (!allTexturesExistent(characterAtlas, itemsAtlas, obstaclesAtlas, surfacesAtlas)) {
            LOGGER.log(Level.SEVERE, Errors.TEXTURES_NOT_FOUND)
        }
    }

    private fun allTexturesExistent(characterAtlas: TextureAtlas, itemsAtlas: TextureAtlas,
                                    obstaclesAtlas: TextureAtlas, surfacesAtlas: TextureAtlas): Boolean {
        return !(characterAtlas.regions.isEmpty || itemsAtlas.regions.isEmpty || obstaclesAtlas.regions.isEmpty || surfacesAtlas.regions.isEmpty)
    }
}