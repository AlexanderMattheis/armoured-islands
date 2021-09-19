package com.gdx.game.system.defaults

object FileExtensions {
    fun stripExtension(filePath: String): String {
        val split = filePath.split(".").toTypedArray()
        return if (split.size == 2) split[0] else ""
    }

    const val MAP_ARCHIVE = ".mapz"
    const val THUMBNAIL = ".jpg"
    const val MAP = ".xml"
}