package com.gdx.game.system.storage

import com.gdx.game.system.defaults.messages.errors.Errors
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class DirectoryScanner {
    companion object {
        private val LOGGER = Logger.getLogger(DirectoryScanner::class.qualifiedName)
    }

    fun getFiles(directoryPath: String, fileExtension: String): List<Path> {
        val files: MutableList<Path> = ArrayList()

        try {
            val paths = Files.walk(Paths.get(directoryPath))
            val filteredPaths = paths.filter { path: Path -> path.toString().endsWith(fileExtension) && path.toFile().isFile }
            filteredPaths.forEach { e: Path -> files.add(e) }
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.PATH_NOT_READABLE, e)
        }

        return files
    }
}