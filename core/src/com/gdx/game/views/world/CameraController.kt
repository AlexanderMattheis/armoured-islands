package com.gdx.game.views.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.system.settings.config.shortcuts.IngameControls
import com.gdx.game.views.screens.widgets.Widgets

class CameraController(settings: Settings, map: Map) {
    companion object {
        private const val CAM_PADDING_LEFT = 200f
        private const val CAM_PADDING_RIGHT = 300f
        private const val CAM_PADDING_VERTICAL = 100f

        private const val MOUSE_ACTIVATION_MARGIN_PIXEL = 1
        private const val SPEED_PIXEL = 350f
    }

    private val camera: OrthographicCamera
    private val mapDimensionsPixel: Vector2
    private val ingameControls: IngameControls

    private val tileSize: Int = settings.graphics.textureSize

    init {
        val dimensions = map.getDimensions()
        mapDimensionsPixel = Vector2(dimensions.x * tileSize, dimensions.y * tileSize)
        ingameControls = settings.controls.ingameControls

        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.translate(mapDimensionsPixel.x / 2.0f, mapDimensionsPixel.y / 2.0f)
        camera.update()
    }

    var cameraPositionPixel: Vector2
        get() = Vector2(camera.position.x, camera.position.y)
        set(position) {
            camera.position.x = position.x
            camera.position.y = position.y
        }


    fun setMapDimensions(mapDimensions: Vector2) {
        mapDimensionsPixel.x = mapDimensions.x * tileSize
        mapDimensionsPixel.y = mapDimensions.y * tileSize
    }

    fun update(batch: SpriteBatch, shapeRenderer: ShapeRenderer, deltaTime: Float) {
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        // controls
        if (!edgeScrolling(deltaTime)) {  // using the mouse or touch
            arrowKeysScrolling(deltaTime)
        }
    }

    private fun edgeScrolling(deltaTime: Float): Boolean {
        val mouseX = Gdx.input.x
        val mouseY = Gdx.input.y

        val width = Gdx.graphics.width
        val height = Gdx.graphics.height

        val isLeft = mouseX in 0..MOUSE_ACTIVATION_MARGIN_PIXEL
        val isRight = mouseX in (width - MOUSE_ACTIVATION_MARGIN_PIXEL)..width
        val isBottom = mouseY in (height - MOUSE_ACTIVATION_MARGIN_PIXEL)..height
        val isTop = mouseY in 0..MOUSE_ACTIVATION_MARGIN_PIXEL

        moveCamera(isLeft, isRight, isBottom, isTop, deltaTime)
        return isLeft || isRight || isBottom || isTop
    }

    private fun arrowKeysScrolling(deltaTime: Float) {
        val isLeft = Gdx.input.isKeyPressed(ingameControls.moveCameraLeft)
        val isRight = Gdx.input.isKeyPressed(ingameControls.moveCameraRight)
        val isBottom = Gdx.input.isKeyPressed(ingameControls.moveCameraBottom)
        val isTop = Gdx.input.isKeyPressed(ingameControls.moveCameraTop)

        if (!Widgets.areFocused) {
            moveCamera(isLeft, isRight, isBottom, isTop, deltaTime)
        }
    }

    private fun moveCamera(moveLeft: Boolean, moveRight: Boolean, moveBottom: Boolean, moveTop: Boolean, deltaTime: Float) {
        if (moveLeft && camera.position.x > CAM_PADDING_LEFT) {
            camera.translate(-deltaTime * SPEED_PIXEL, 0f)
        }

        if (moveRight && camera.position.x < mapDimensionsPixel.x - CAM_PADDING_RIGHT) {
            camera.translate(deltaTime * SPEED_PIXEL, 0f)
        }

        if (moveBottom && camera.position.y > CAM_PADDING_VERTICAL) {
            camera.translate(0f, -deltaTime * SPEED_PIXEL)
        }

        if (moveTop && camera.position.y < mapDimensionsPixel.y - CAM_PADDING_VERTICAL) {
            camera.translate(0f, deltaTime * SPEED_PIXEL)
        }

        // to reset if user gets outside the visible area (for example in the editor or due to inaccuracies)
        if (camera.position.x < CAM_PADDING_LEFT) {
            camera.position.x = CAM_PADDING_LEFT
        }

        if (camera.position.x > mapDimensionsPixel.x - CAM_PADDING_RIGHT) {
            camera.position.x = mapDimensionsPixel.x - CAM_PADDING_RIGHT
        }

        if (camera.position.y < CAM_PADDING_VERTICAL) {
            camera.position.y = CAM_PADDING_VERTICAL
        }

        if (camera.position.y > mapDimensionsPixel.y - CAM_PADDING_VERTICAL) {
            camera.position.y = mapDimensionsPixel.y - CAM_PADDING_VERTICAL
        }

        camera.update()
    }
}