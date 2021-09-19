package com.gdx.game.views.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.gdx.game.Main
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.Paths.Files.MENU_STYLE
import com.gdx.game.system.media.MusicPlayer
import com.gdx.game.views.screens.stages.menus.MainMenu

class MenuScreen private constructor() : ScreenAdapter(), Screen {
    companion object {
        private val instance = MenuScreen()

        fun getInstance(): MenuScreen {
            Gdx.input.inputProcessor = instance.menus[instance.menus.count() - 1]
            MusicPlayer.play(Main.manager.get(Paths.Files.Music.MENU, Music::class.java))
            return instance
        }
    }

    private val spriteBatch: SpriteBatch = SpriteBatch()
    private val menus: MutableList<Stage> = mutableListOf()

    init {
        menus.add(MainMenu(Skin(Gdx.files.internal(MENU_STYLE))))
        MusicPlayer.play(Main.manager.get(Paths.Files.Music.MENU, Music::class.java))
    }

    fun pushMenu(stage: Stage) {
        menus.add(stage)
    }

    fun popMenu(): Stage {
        val stage = menus.removeAt(menus.count() - 1)
        Gdx.input.inputProcessor = menus[menus.count() - 1]  // to take input from ui
        return stage
    }

    override fun render(deltaTime: Float) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        spriteBatch.begin()
        update(deltaTime)
        draw(deltaTime)
        spriteBatch.end()

        // IMPORTANT: do not add this into the sprite-batch
        val topMenu = menus[menus.count() - 1]
        topMenu.act(deltaTime)  // to draw time dependant stuff like the hover
        topMenu.draw()
    }

    override fun update(deltaTime: Float) { // NOP
    }

    override fun draw(deltaTime: Float) { // NOP
    }

    override fun resize(width: Int, height: Int) {
        val topMenu = menus[menus.count() - 1]
        topMenu.viewport.update(width, height, true)
    }

    override fun dispose() {
        super.dispose()
        val topMenu = menus[menus.count() - 1]
        topMenu.dispose()
        spriteBatch.dispose()
    }
}