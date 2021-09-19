package com.gdx.game.views.screens.stages.huds.parts

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.widgets.Paddings

class BackgroundWindow(skin: Skin) : Window("", skin, Names.Panel.EDITOR_HUD) {
    init {
        // add
        Proportioner.setPadding(this, Paddings(0f, 0.00868f, 0.0347f, 0.00868f))
    }
}