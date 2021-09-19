package com.gdx.game.views.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.gdx.game.Main.Companion.instance
import com.gdx.game.Main.Companion.manager
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.storage.profile.CreateProfileRunner

class LoadingScreen : ScreenAdapter(), Screen {
    companion object {
        private val MANAGER = manager
    }

    private val createProfileRunner: CreateProfileRunner = CreateProfileRunner(Paths.USER_FOLDERS, Paths.FILES, Paths.USER_FILES)

    init {
        loadIntoQueue(Paths.SPRITES, Paths.Files.ATLASES)
    }

    private fun loadIntoQueue(path: String, fileNames: Array<String>) {
        MANAGER.load(Paths.Files.Music.MENU, Music::class.java)
        MANAGER.load(Paths.Files.Music.INGAME, Music::class.java)

        for (fileName in fileNames) {
            MANAGER.load(path + fileName, TextureAtlas::class.java)
        }
    }

    override fun render(deltaTime: Float) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        update(deltaTime)
        draw(deltaTime)
    }

    override fun update(deltaTime: Float) {
        when {
            !createProfileRunner.isFinished -> createProfileRunner.executeStep()
            !MANAGER.isFinished -> MANAGER.update()  // true, when everything has been loaded
            else -> instance.screen = MenuScreen.getInstance()
        }
    }

    override fun draw(deltaTime: Float) { // NOP
    }
}