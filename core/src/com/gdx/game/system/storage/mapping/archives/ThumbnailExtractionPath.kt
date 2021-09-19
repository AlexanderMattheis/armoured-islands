package com.gdx.game.system.storage.mapping.archives

import com.gdx.game.system.defaults.Paths.PATHS_SEPARATOR
import com.gdx.game.system.defaults.views.world.MapArchiveDefaults.TEMP_NAME_PREFIX_SEPARATOR
import java.nio.file.Path

object ThumbnailExtractionPath {

    fun getMapName(thumbnailPath: Path): String {
        val split = thumbnailPath.fileName.toString().split(TEMP_NAME_PREFIX_SEPARATOR).toTypedArray()
        return if (split.isNotEmpty()) split[0] else ""
    }


    fun getFileDestinationPath(destinationPath: String, fileToExtractName: String, namePrefixToAdd: String): String {
        return destinationPath + PATHS_SEPARATOR + namePrefixToAdd + TEMP_NAME_PREFIX_SEPARATOR + fileToExtractName
    }
}