package com.gdx.game.views.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.gdx.game.Main
import com.gdx.game.Main.Companion.manager
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.Paths.Files.MENU_STYLE
import com.gdx.game.system.media.MusicPlayer
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.system.storage.mapping.textures.MapTexturesCache
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.screens.stages.huds.EditorHud
import com.gdx.game.views.screens.stages.huds.Hud
import com.gdx.game.views.screens.stages.huds.SinglePlayerHud
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType

class GameScreen(settings: Settings, map: Map?, editMode: Boolean) : ScreenAdapter(), Screen {
    companion object {
        private const val PADDING_FOR_CULLING = 80
    }

    private val hud: Hud
    private val cameraController: CameraController
    private val map: Map
    private val texturesCache: MapTexturesCache
    private val spriteBatch: SpriteBatch
    private val shapeRenderer: ShapeRenderer
    private val tileSize: Int
    private var totalElapsedTime: Float
    private var dialogInFront: Boolean


    init {
        val skin = Skin(Gdx.files.internal(MENU_STYLE))

        spriteBatch = SpriteBatch()
        shapeRenderer = ShapeRenderer()

        if (editMode) {
            MusicPlayer.play(manager.get(Paths.Files.Music.INGAME, Music::class.java)) // TODO: Change music to something else
            hud = EditorHud(manager, settings, map, skin, shapeRenderer)

            this.map = hud.map
            texturesCache = hud.texturesCache
            cameraController = hud.cameraController
        } else {
            MusicPlayer.play(manager.get(Paths.Files.Music.INGAME, Music::class.java))
            hud = SinglePlayerHud(manager, settings, map, skin)

            this.map = hud.map
            texturesCache = hud.texturesCache
            cameraController = hud.cameraController
        }

        tileSize = settings.graphics.textureSize
        totalElapsedTime = 0.0f
        dialogInFront = false
    }

    override fun render(deltaTime: Float) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 0.808f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        spriteBatch.begin()
        update(deltaTime)
        draw(deltaTime)
        spriteBatch.end()

        drawStages(deltaTime)
    }

    private fun drawStages(deltaTime: Float) {  // IMPORTANT: do not add this into the sprite-batch
        hud.act(deltaTime)
        hud.draw()

        for (dialog in hud.dialogs) {
            dialog.act(deltaTime)
            dialog.draw()
        }

        dialogInFront = hud.dialogs.any { obj: Dialog -> obj.isVisible }
    }

    override fun update(deltaTime: Float) {
        if (!dialogInFront) {
            cameraController.update(spriteBatch, shapeRenderer, deltaTime)
            totalElapsedTime += deltaTime
        }
    }

    override fun draw(deltaTime: Float) {
        drawObjects(ObjectType.SURFACES, map.getSurfaces())
        drawObjects(ObjectType.ITEMS, map.getItems())
        drawObjects(ObjectType.CHARACTERS, map.getCharacters())
        drawObjects(ObjectType.OBSTACLES, map.getObstacles())
    }

    private fun drawObjects(type: ObjectType, objects: List<GameObject>) {
        for (gameObject in objects) {

            val id = gameObject.id
            val textureRef = gameObject.textureRef
            val position = gameObject.position
            val rotation = gameObject.rotation.toFloat()

            val textureRegion: TextureRegion = if (textureRef.isAnimated) {
                texturesCache.getTexture(type, id, totalElapsedTime)
            } else {
                texturesCache.getTexture(type, id)
            }

            if (!isWithinScreenRectangle(position)) {
                continue
            }

            drawTexture(gameObject, textureRegion, position, rotation)
        }
    }

    private fun drawTexture(gameObject: GameObject, textureRegion: TextureRegion, position: Vector2, rotation: Float) {
        val selectedObject = hud.selectedObject

        if (gameObject == selectedObject) {
            spriteBatch.color = Color.LIGHT_GRAY
            spriteBatch.draw(textureRegion, position.x * tileSize, position.y * tileSize, tileSize / 2.0f, tileSize / 2.0f,
                    tileSize.toFloat(), tileSize.toFloat(), 1.0f, 1.0f, rotation)
            spriteBatch.color = Color.WHITE
        } else {
            spriteBatch.draw(textureRegion, position.x * tileSize, position.y * tileSize, tileSize / 2.0f, tileSize / 2.0f,
                    tileSize.toFloat(), tileSize.toFloat(), 1.0f, 1.0f, rotation)
        }
    }

    /**
     * The camera position is in the centre of the screen.
     */
    private fun isWithinScreenRectangle(position: Vector2): Boolean {
        val cameraPositionPixel = cameraController.cameraPositionPixel
        val nearlyHalfScreenWidthPixel = Gdx.graphics.width / 2 + PADDING_FOR_CULLING
        val nearlyHalfScreenHeightPixel = Gdx.graphics.height / 2 + PADDING_FOR_CULLING
        val withinXBounds = (position.x * tileSize >= cameraPositionPixel.x - nearlyHalfScreenWidthPixel
                && position.x * tileSize <= cameraPositionPixel.x + nearlyHalfScreenWidthPixel)
        val withinYBounds = (position.y * tileSize >= cameraPositionPixel.y - nearlyHalfScreenHeightPixel
                && position.y * tileSize <= cameraPositionPixel.y + nearlyHalfScreenHeightPixel)
        return withinXBounds && withinYBounds
    }

    override fun resize(width: Int, height: Int) {
        hud.viewport.update(width, height, true)
    }

    override fun dispose() {
        hud.dispose()
    }
}