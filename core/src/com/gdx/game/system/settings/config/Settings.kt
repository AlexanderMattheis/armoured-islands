package com.gdx.game.system.settings.config

import com.gdx.game.system.defaults.messages.errors.Errors
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class Settings(systemSettingsPath: String, controlsConfigPath: String) {
    companion object {
        private val LOGGER = Logger.getLogger(Settings::class.qualifiedName)
    }

    var controls: Controls
    var graphics: Graphics

    init {
        val systemSettings = loadConfiguration(systemSettingsPath)
        val shortcuts = loadConfiguration(controlsConfigPath)
        graphics = Graphics(systemSettings)
        controls = Controls(shortcuts)
    }

    private fun loadConfiguration(path: String): Properties {
        val settings = Properties()

        try {
            FileInputStream(path).use { input -> settings.load(input) }
        } catch (e: FileNotFoundException) {
            LOGGER.log(Level.SEVERE, Errors.NOT_EXISTENT_CONFIG, e)
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.READING_FILE, e)
        }

        return settings
    }
}