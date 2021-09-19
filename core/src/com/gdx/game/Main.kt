package com.gdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.gdx.game.views.screens.LoadingScreen

class Main private constructor() : Game() {
    companion object {
        val instance = Main()
        val manager = AssetManager()
        var leftMarginPercent = 0f
            private set
        var windowHeightPixel = 0f
            private set

        /**
         * |---------------------------------------------|
         * ~38%             100% (1:1)           ~38%
         * |-------|-----------------------------|-------|
         * _______________________________
         * |                             |
         * |                             |
         * |                             |
         * |                             |
         * |                             |
         * |                             |
         * |                             |
         * |                             |
         * |                             |
         * |_____________________________|
         */
        //
        private fun initResolution() {
            val width = Gdx.graphics.width.toFloat()
            val height = Gdx.graphics.height.toFloat()
            val aspectRatio = width / height // e.g. 16/9 = ~1.77
            leftMarginPercent = (aspectRatio - 1) / 2 // e.g. (~1.77 - 1) / 2 = ~0.77 / 2 = ~0.38
            windowHeightPixel = height
        }
    }

    override fun create() {
        initResolution()
        setScreen(LoadingScreen())
    }
}