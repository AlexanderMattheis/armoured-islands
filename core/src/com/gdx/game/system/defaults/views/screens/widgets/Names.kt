package com.gdx.game.system.defaults.views.screens.widgets

object Names {
    const val DIALOG_WINDOW = "dialog-panel"
    const val INPUT_FIELD = "input-field"

    object Image {
        const val COMPANY_LOGO = "company-logo"
        const val GAME_LOGO = "game-logo"
    }

    object Button {
        private const val ICON_BUTTON = "icon-button_"

        const val BACK = "back-button"
        const val BUTTON = "button"
        const val EXIT = "exit-button"
        const val FILE = "file-button"
        const val FOLDER = "folder-button"
        const val MENU_FILE = "menu-file-button"
        const val MIRROR = "mirror-button"
        const val PIN = "pin-button"
        const val ROTATION = "rotation-button"
        const val TAB = "tab-button"

        object IconButton {
            const val COPY = ICON_BUTTON + "copy"
            const val CUT = ICON_BUTTON + "cut"
            const val EXIT = ICON_BUTTON + "exit"
            const val HISTORY_DIRECTION = ICON_BUTTON + "history-direction_"
            const val PASTE = ICON_BUTTON + "paste"
            const val SAVE = ICON_BUTTON + "save"
            const val SELECT = ICON_BUTTON + "select"

            object HistoryDirection {
                const val LEFT = HISTORY_DIRECTION + "left"
                const val RIGHT = HISTORY_DIRECTION + "right"
            }
        }

        object NumberInputButton {
            const val NUMBER_INPUT_BUTTON_UP = "number-input-button_up"
            const val NUMBER_INPUT_BUTTON_DOWN = "number-input-button_down"
        }

        object ScrollButton {
            const val DOWN = "scrollbar-button_vertical_down"
            const val UP = "scrollbar-button_vertical_up"
        }
    }

    object Panel {
        const val EDITOR_HUD = "editor-hud-panel"
        const val HORIZONTAL_PANEL = "horizontal-panel"
        const val TWO_BUTTON = "two-button-panel"
    }
}