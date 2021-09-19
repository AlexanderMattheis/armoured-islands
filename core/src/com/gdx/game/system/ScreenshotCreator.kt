package com.gdx.game.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.utils.BufferUtils
import com.badlogic.gdx.utils.ScreenUtils

object ScreenshotCreator {
    fun take(): ByteArray {
        val pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.backBufferWidth, Gdx.graphics.backBufferHeight, false)
        setOpaque(pixels)  // making screenshot opaque

        val screenshot = Pixmap(Gdx.graphics.backBufferWidth, Gdx.graphics.backBufferHeight, Pixmap.Format.RGBA8888)
        BufferUtils.copy(pixels, 0, screenshot.pixels, pixels.size)
        screenshot.dispose()

        return pixels
    }

    private fun setOpaque(pixels: ByteArray) {
        for (i in 4..pixels.size step 4) {
            pixels[i - 1] = 255.toByte()  // TODO: replace with 255u when unsigned bytes allowed in Kotlin
        }
    }
}