package com.gdx.game.system.settings.config

import com.gdx.game.system.settings.config.shortcuts.EditorControls
import com.gdx.game.system.settings.config.shortcuts.IngameControls
import java.util.*

class Controls(shortcuts: Properties) {
    val editorControls: EditorControls = EditorControls(shortcuts)
    val ingameControls: IngameControls = IngameControls(shortcuts)

}