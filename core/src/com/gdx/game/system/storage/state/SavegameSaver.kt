package com.gdx.game.system.storage.state

import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.logging.Level
import java.util.logging.Logger

object SavegameSaver {
    private val LOGGER = Logger.getLogger(SavegameSaver::class.qualifiedName)

    fun save(path: String, savegame: Savegame) {
        try {
            val output = FileOutputStream(path)
            val objectOutputStream = ObjectOutputStream(output)
            objectOutputStream.writeObject(savegame)
            objectOutputStream.close()
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.SAVING_STATE, e)
        }
    }

    fun createFrom(map: Map, cameraController: CameraController): Savegame {
        return Savegame(map, cameraController.cameraPositionPixel)
    }
}