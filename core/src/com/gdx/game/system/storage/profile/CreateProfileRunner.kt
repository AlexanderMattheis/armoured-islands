package com.gdx.game.system.storage.profile

import com.gdx.game.system.defaults.messages.Informations
import com.gdx.game.system.defaults.messages.errors.Errors
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.logging.Level
import java.util.logging.Logger

class CreateProfileRunner(private val directoryPaths: Array<String>, private val copyFromPath: Array<String>, private val copyToPath: Array<String>) : StepsRunner(directoryPaths.size + copyToPath.size) {
    companion object {
        private val LOGGER = Logger.getLogger(CreateProfileRunner::class.qualifiedName)
    }

    override fun executeStep() {
        if (currentNumberOfSteps < directoryPaths.size) {
            processDirectoryPath(currentNumberOfSteps)
        } else {
            processFilePath(currentNumberOfSteps - directoryPaths.size)
        }
    }

    private fun processDirectoryPath(step: Int) {
        val directoryFile = File(directoryPaths[step])
        currentNumberOfSteps++

        if (directoryFile.exists()) {
            return
        }

        val directoryCreated = directoryFile.mkdir()

        if (!directoryCreated) {
            val directoryName = directoryFile.name
            addExceptionsInfo(IOException(), Errors.CREATING_DIR + directoryName)
            LOGGER.log(Level.INFO, Informations.CREATING_DIR + directoryName)
        }
    }

    private fun processFilePath(step: Int) {
        require(copyFromPath.size == copyToPath.size) { Errors.ARRAYS_SAME_SIZE }

        val fromFile = File(copyFromPath[step])
        val toFile = File(copyToPath[step])
        currentNumberOfSteps++

        require(fromFile.exists()) { Errors.NOT_EXISTENT_FILE }

        if (toFile.exists()) {  // should not exist
            return
        }

        try {
            Files.copy(fromFile.toPath(), toFile.toPath())
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.COPYING_FILE + fromFile, e)
        }
    }
}