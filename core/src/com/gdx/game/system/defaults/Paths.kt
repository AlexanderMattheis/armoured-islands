package com.gdx.game.system.defaults

import com.gdx.game.system.defaults.Paths.User.Folders
import com.gdx.game.system.settings.OsDetector
import javax.swing.filechooser.FileSystemView

object Paths {
    const val PATHS_SEPARATOR = "/"  // LibGDX can only handle "/", so File.separator cannot be used to handle it manually
    const val CONFIG = ".." + PATHS_SEPARATOR + "config" + PATHS_SEPARATOR  // hint: this path is only used to copy data into the user folder 'config'
    const val MAPS = ".." + PATHS_SEPARATOR + "maps" + PATHS_SEPARATOR  // hint: this path is for official maps
    const val SPRITES = "sprites" + PATHS_SEPARATOR + "default" + PATHS_SEPARATOR
    const val MUSIC = "music" + PATHS_SEPARATOR

    val IS_WINDOWS = OsDetector.isWindows
    val ALIEN_ISLAND_FOLDER = if (IS_WINDOWS) "Armoured Islands" else ".armoured-islands"
    val TEMP_FOLDER_PATH = System.getProperty("java.io.tmpdir") + if (IS_WINDOWS) "" else PATHS_SEPARATOR + ALIEN_ISLAND_FOLDER
    val USER_FOLDER_PATH = FileSystemView.getFileSystemView().defaultDirectory.path + PATHS_SEPARATOR + ALIEN_ISLAND_FOLDER

    val FILES = arrayOf(Files.Configuration.CONTROLS, Files.Configuration.SYSTEM)
    val USER_FILES = arrayOf(Folders.Files.CONTROLS, Folders.Files.SYSTEM)
    val USER_FOLDERS = arrayOf(USER_FOLDER_PATH, Folders.CONFIG, Folders.MAPS)

    object Files {
        val ATLASES = arrayOf(Atlases.CHARACTERS_ATLAS, Atlases.ITEMS_ATLAS, Atlases.OBSTACLES_ATLAS, Atlases.SURFACES_ATLAS, Atlases.DEFAULT_MENU_ATLAS)
        const val INTRO_MAP = MAPS + "000.xml"
        const val LOGO = SPRITES + "game-logo.png"
        const val MENU_STYLE = SPRITES + "default.json"

        object Atlases {
            const val CHARACTERS_ATLAS = "characters.atlas"
            const val ITEMS_ATLAS = "items.atlas"
            const val OBSTACLES_ATLAS = "obstacles.atlas"
            const val SURFACES_ATLAS = "surfaces.atlas"
            const val DEFAULT_MENU_ATLAS = "default.atlas"
        }

        object Configuration {
            const val CONTROLS = CONFIG + "controls.properties" // hint: this file is for copying only
            const val SYSTEM = CONFIG + "system.properties" // hint: this file is for copying only
        }

        object Music {
            const val MENU = MUSIC + "menu.mp3"
            const val INGAME = MUSIC + "ingame.mp3"
        }
    }

    object Temp {
        val MAPS = TEMP_FOLDER_PATH + PATHS_SEPARATOR + "maps"

        object Maps {
            val LOADING = MAPS + PATHS_SEPARATOR + "loading"
            val THUMBNAILS = MAPS + PATHS_SEPARATOR + "thumbnails"
        }
    }

    object User {
        object Folders {
            val CONFIG = USER_FOLDER_PATH + PATHS_SEPARATOR + "config"
            val MAPS = USER_FOLDER_PATH + PATHS_SEPARATOR + "maps" // hint: this path is for downloaded maps and user maps

            object Files {
                val CONTROLS = CONFIG + PATHS_SEPARATOR + "controls.properties"
                val SYSTEM = CONFIG + PATHS_SEPARATOR + "system.properties"
            }
        }
    }
}