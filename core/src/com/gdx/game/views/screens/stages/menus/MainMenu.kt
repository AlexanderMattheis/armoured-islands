package com.gdx.game.views.screens.stages.menus

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions.Button.MENU
import com.gdx.game.system.defaults.views.screens.widgets.Fonts.LABEL
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.MenuScreen.Companion.getInstance
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.StageAdder
import com.gdx.game.views.screens.stages.MenuFloating

class MainMenu(skin: Skin) : Stage() {

    init {
        // declare
        val buttonPosition = Vector2(0.5f, 0.608f)
        val buttonSpaces = Vector2(0f, 0.095f)

        // create
        val imgGameLogo = Button(skin, Names.Image.GAME_LOGO)
        val btnPlay = TextButton("Play", skin, Names.Button.BUTTON)
        val btnEdit = TextButton("Edit", skin, Names.Button.BUTTON)
        val btnSettings = TextButton("Settings", skin, Names.Button.BUTTON)
        val btnExit = TextButton("Exit", skin, Names.Button.BUTTON)
        val btnCredits = TextButton("Credits", skin, Names.Button.BUTTON)
        val actors = arrayOf<Actor>(btnPlay, btnEdit, btnSettings, btnExit, btnCredits)

        // adjust
        Proportioner.setFontSize(skin, LABEL, 0.000521f)

        // link
        linkButtons(btnPlay, btnEdit, btnExit, skin)

        // add
        StageAdder.addTo(imgGameLogo, this, Vector2(buttonPosition.x - 0.01f, 0.78f),
                Vector2(MENU.x, 0.193f), Align.center, MenuFloating.CENTER)

        btnSettings.isDisabled = true
        btnCredits.isDisabled = true

        setButtons(actors, buttonPosition, buttonSpaces)
        Gdx.input.inputProcessor = this  // to take input from ui
    }

    private fun linkButtons(btnPlay: Button, btnEdit: Button, btnExit: Button, skin: Skin) {
        btnPlay.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                getInstance().pushMenu(GameMenu(skin))
            }
        })

        btnEdit.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                getInstance().pushMenu(EditMenu(skin))
            }
        })

        btnExit.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                Gdx.app.exit()
            }
        })
    }

    private fun setButtons(actors: Array<Actor>, position: Vector2, spaces: Vector2) {
        Proportioner.setDimensions(actors, MENU)
        Proportioner.setPositions(actors.copyOfRange(0, 3), position, spaces, Align.center, MenuFloating.CENTER)
        Proportioner.setPosition(actors[3], Vector2(position.x, 0.122f), Align.center, MenuFloating.CENTER)
        Proportioner.setPosition(actors[4], Vector2(0.087f, 0.122f), Align.left + Align.center, MenuFloating.LEFT)
        StageAdder.addTo(this, actors)
    }
}