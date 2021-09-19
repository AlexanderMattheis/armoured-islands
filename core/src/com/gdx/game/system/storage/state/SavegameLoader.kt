package com.gdx.game.system.storage.state

import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.views.world.CameraController
import com.gdx.game.views.world.Map
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.util.logging.Level
import java.util.logging.Logger

object SavegameLoader {
    private val LOGGER = Logger.getLogger(SavegameLoader::class.qualifiedName)

    fun load(path: String): Savegame? {
        var savegame: Savegame? = null

        try {
            val fileIn = FileInputStream(path)
            val objectInputStream = ObjectInputStream(fileIn)
            savegame = objectInputStream.readObject() as Savegame
            objectInputStream.close()
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.LOADING_STATE, e)
        } catch (e: ClassNotFoundException) {
            LOGGER.log(Level.SEVERE, Errors.CLASS_NOT_FOUND, e)
        }

        return savegame
    }

    fun restoreAll(savegame: Savegame, map: Map, cameraController: CameraController) {
        restoreMap(savegame, map)
        restoreCameraPosition(savegame, cameraController)
    }

    private fun restoreMap(savegame: Savegame, map: Map) {  // TODO: just create copy of map
        val savedMap = savegame.map
        map.name = savedMap.name
        
        map.setDimensions(savedMap.getDimensions())
        map.setCharacters(savedMap.getCharacters())
        map.setItems(savedMap.getItems())
        map.setObstacles(savedMap.getObstacles())
        map.setSurfaces(savedMap.getSurfaces())
    }

    private fun restoreCameraPosition(savegame: Savegame, cameraController: CameraController) {
        cameraController.cameraPositionPixel = savegame.cameraPosition
    }
}