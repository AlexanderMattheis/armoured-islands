package com.gdx.game.views.screens.stages.huds

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Fonts
import com.gdx.game.system.defaults.views.world.EditorDefaults
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.system.settings.config.shortcuts.EditorControls
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.views.screens.compositing.Adjuster
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.StageAdder
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.MenuFloating
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.huds.initializers.EscapeDialogInitializer
import com.gdx.game.views.screens.stages.huds.initializers.SaveDialogInitializer
import com.gdx.game.views.screens.stages.huds.parts.*
import com.gdx.game.views.screens.stages.huds.playgrounds.EditorHudPlayground
import com.gdx.game.views.screens.widgets.ActorFloating
import com.gdx.game.views.screens.widgets.Paddings
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.editor.MapEditor
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class EditorHud(manager: AssetManager, settings: Settings, map: Map?, skin: Skin, private val shapeRenderer: ShapeRenderer) : Hud() {
    companion object {
        private val LOGGER = Logger.getLogger(EditorHud::class.qualifiedName)
    }

    private val btnUndo: Button
    private val btnRedo: Button

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
        get() = editor.selectedObject

    val hoveredObject: GameObject?
        get() = editor.hoveredObject

    val isObjectPlaceable: Boolean
        get() = editor.isObjectPlaceable

    val texturesCache: MapTexturesCache
        get() = editor.texturesCache

    val cameraController: CameraController
        get() = editor.cameraController

    val map: Map
        get() = editor.map

    private val tileSize: Int

    init {
        init(skin, manager)
        initDialogs(skin)
        tileSize = settings.graphics.textureSize

        // define
        val controls = settings.controls
        editorControls = controls.editorControls
        editor = MapEditor(map, manager, settings, ObjectType.CHARACTERS, characterAtlas!!.regions[0].name, surfacesAtlas!!.regions[0].name)

        // create
        val window = BackgroundWindow(skin)
        val topBar = TopBar(skin, this)
        val dimensionsPanel = DimensionsPanel(skin, this)
        val typeObjectsPanel = TypeObjectsPanel(skin, this)
        val screenButtonsWindow = ScreenButtonsWindow(skin, this)
        EditorHudPlayground.instance.link(this, settings)

        // adjust
        Adjuster.enableTextFieldFocusLoss(this)
        btnUndo = topBar.btnUndo
        btnRedo = topBar.btnRedo
        Gdx.input.inputProcessor = this // to take input from ui

        // add
        TableAdder.addTo(window, topBar, Paddings(0.002f, 0f, 0f, 0f), arrayOf(ActorFloating.TOP), 1, true)
        TableAdder.addTo(window, dimensionsPanel, 1, true)
        TableAdder.addTo(window, typeObjectsPanel, 1, true)
        StageAdder.addTo(this, window, Vector2(0f, 0f), Vector2(0.477f, 1.0f), MenuFloating.LEFT)
        StageAdder.addTo(this, screenButtonsWindow, Vector2(0.65f, 0.04f), Vector2(0.233f, 0.1198f), MenuFloating.RIGHT)
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

    private fun initDialogs(skin: Skin) {
        escapeDialog = EscapeDialogInitializer.instance.createDialog(skin, this)
        saveDialog = SaveDialogInitializer.instance.createDialog(skin, this)
        saveQuitDialog = SaveDialogInitializer.instance.createDialog(skin, this, true)
    }

    fun reactivateChangesHistory() {
        btnUndo.isDisabled = false
        btnRedo.isDisabled = true
    }

    override fun draw() {
        super.draw()
        drawRectangleAroundObjects()
    }

    // IMPORTANT: do not change the order within the method
    private fun drawRectangleAroundObjects() {
        val hoveredObject = hoveredObject

        if (hoveredObject != null) {
            if (isObjectPlaceable) {
                drawRectangle(hoveredObject, EditorDefaults.MarkerColoring.HOVER)
            } else {
                drawRectangle(hoveredObject, EditorDefaults.MarkerColoring.ERROR)
            }
        }

        val selectedObject = selectedObject

        if (selectedObject != null) {
            drawRectangle(selectedObject, EditorDefaults.MarkerColoring.SELECT)
        }
    }

    private fun drawRectangle(gameObject: GameObject, color: Color) {
        val position = gameObject.position
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = color
        shapeRenderer.rect(position.x * tileSize, position.y * tileSize, tileSize.toFloat(), tileSize.toFloat())
        shapeRenderer.end()
    }
}