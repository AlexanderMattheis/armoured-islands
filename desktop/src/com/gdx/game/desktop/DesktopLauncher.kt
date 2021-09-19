package com.gdx.game.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.gdx.game.Main.Companion.instance
import com.gdx.game.desktop.SpritesheetCreator.TYPES
import com.gdx.game.desktop.system.defaults.Params

object DesktopLauncher {

    @JvmStatic  // this annotation is necessary because the game is started from a Java library
    fun main(args: Array<String>) {
        val windowWidth = Params.Window.DIMENSIONS.x.toInt()
        val windowHeight = Params.Window.DIMENSIONS.y.toInt()

        val config = Lwjgl3ApplicationConfiguration()
        config.setResizable(false)
        config.setWindowedMode(windowWidth, windowHeight)
        config.setTitle(Params.Window.TITLE)
        config.useVsync(true)

        // TODO: add possibility to load a custom icon from profile file (configuration) passed through String[] args
        setIcon(config, Params.Window.Icon.PATH, Params.Window.Icon.NAME, Params.Window.Icon.RESOLUTIONS, Params.Window.Icon.DATA_TYPE)
        Lwjgl3Application(instance, config)
//        SpritesheetCreator.createSpritesheets(TYPES)
    }

    private fun setIcon(config: Lwjgl3ApplicationConfiguration, path: String, name: String, resolutions: IntArray, dataType: String) {
        val iconStrings = arrayOfNulls<String>(resolutions.size)

        for (i in resolutions.indices) {
            val resolution = resolutions[i]
            val iconString = path + name + "_" + resolution + dataType
            iconStrings[i] = iconString
        }

        config.setWindowIcon(*iconStrings)
    }
}