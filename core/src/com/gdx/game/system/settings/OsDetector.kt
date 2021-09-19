package com.gdx.game.system.settings

object OsDetector {
    val isWindows: Boolean
        get() = osName.startsWith("Windows")

    private val osName: String
        get() = System.getProperty("os.name")
}