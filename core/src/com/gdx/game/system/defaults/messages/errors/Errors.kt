package com.gdx.game.system.defaults.messages.errors

import com.gdx.game.system.defaults.views.screens.widgets.Constraints.MAX_NUMBER_MENU_BUTTONS
import com.gdx.game.system.defaults.views.screens.widgets.Constraints.MAX_NUMBER_DIALOG_CHECKBOXES
import com.gdx.game.system.defaults.views.screens.widgets.Constraints.MAX_NUMBER_DIALOG_BUTTONS
import com.gdx.game.system.defaults.views.screens.widgets.Constraints.MAX_NUMBER_MENU_TABS

object Errors {
    const val ARRAYS_SAME_SIZE = "The arrays have to be both the same size!"
    const val ATLAS_NULL = "The texture-atlas shouldn't be null!"
    const val CLASS_NOT_FOUND = "The class couldn't be found!"
    const val CONVERTING_XML_TREE_TO_FILE = "An error has occurred while converting the XML document to a file!"
    const val COPYING_FILE = "Could not copy the file: "
    const val CREATING_DIR = "Could not create the directory: "
    const val CREATING_MAPZ = "An error has occurred while creating the *.mapz-archive!"
    const val CREATING_XML = "An error has occurred while creating the *.map-file!"
    const val DIALOG_NULL = "The dialog shouldn't be null!"
    const val LOADING_STATE = "Failed to load the state of the game!"
    const val NO_SUCH_ELEMENT = "The element under the requested ID does not exist!"
    const val NOT_EXISTENT_CONFIG = "The settings config couldn't be found!"
    const val NOT_EXISTENT_FILE = "The file does not exist: "
    const val NOT_EXISTENT_TYPE = "The requested type does not exist!"
    const val PARSING_XML = "An error has occurred while parsing the XML file!"
    const val PATH_NOT_READABLE = "The given file path was not readable!"
    const val READING_FILE = "An error has occurred while reading the file!"
    const val SAVING_STATE = "Failed to save the state of the game!"
    const val TEXTURES_NOT_FOUND = "It should exist at least one texture of each type!"
    const val TOO_MANY_CHECKBOXES = "A dialog can only hold $MAX_NUMBER_DIALOG_CHECKBOXES checkbox(es)!"
    const val TOO_MANY_DIALOG_BUTTONS = "A dialog can only hold $MAX_NUMBER_DIALOG_BUTTONS button(s)!"
    const val TOO_MANY_HORIZONTAL_MENU_BUTTONS = "A horizontal menu can only hold $MAX_NUMBER_MENU_BUTTONS button(s)!"
    const val TOO_MANY_MENU_TABS = "A horizontal menu can only hold $MAX_NUMBER_MENU_TABS tab(s)!"
    const val WRONG_CONFIG = "Something was wrong with the given configuration!"
    const val WRONG_NUMBER_PATHS = "The number of paths was wrong!"
    const val WRONG_NUMBER_ROOT_NODES = "There should be exactly one root in the tree!"

    object Map {
        const val BUILDING_MAP = "Failed to build the map from the XML file."
        const val COULD_NOT_EXTRACT_THUMBNAILS = "The thumbnails could not be extracted!"
        const val COULD_NOT_DELETE_THUMBNAIL = "The thumbnail could not be deleted."
        const val COUNT_MAP_ROOT = "The 'map' root tag should be present once."
        const val MAP_NOT_LOADED = "The map couldn't be loaded!"
        const val MAP_THUMBNAIL_NOT_LOADED = "The map thumbnail couldn't be loaded!"
        const val THUMBNAIL_NOT_RIGHT_SIZE = "The thumbnail has not the right size!"
    }
}