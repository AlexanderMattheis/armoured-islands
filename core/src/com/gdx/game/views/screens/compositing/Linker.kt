package com.gdx.game.views.screens.compositing

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.gdx.game.Main
import com.gdx.game.views.screens.MenuScreen

object Linker {
    fun linkMenuBackButton(btnBack: Button) {
        btnBack.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                MenuScreen.getInstance().popMenu()
            }
        })
    }

    fun linkGameExitButton(btnExit: Button) {
        btnExit.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                Main.Companion.instance.screen = MenuScreen.getInstance()
            }
        })
    }
}