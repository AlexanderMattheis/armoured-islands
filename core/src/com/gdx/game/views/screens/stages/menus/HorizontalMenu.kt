package com.gdx.game.views.screens.stages.menus

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.gdx.game.system.defaults.Colors
import com.gdx.game.system.defaults.views.screens.widgets.Constraints
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions
import com.gdx.game.system.defaults.views.screens.widgets.Velocities
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.Adjuster
import com.gdx.game.views.screens.compositing.Linker
import com.gdx.game.views.screens.compositing.adder.StageAdder
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.MenuFloating
import com.gdx.game.views.screens.widgets.groups.HorizontalFilterGroup

open class HorizontalMenu(skin: Skin) : Stage() {
    private var numberOfButtons = 0
    private var numberOfTabs = 0

    val scrollUpButton: Button = Button(skin, Names.Button.ScrollButton.UP)
    val scrollDownButton: Button = Button(skin, Names.Button.ScrollButton.DOWN)
    val scrollPane: ScrollPane

    private val tabsGroup: ButtonGroup<Button>
    private val horizontalFilterGroup: HorizontalFilterGroup

    init {
        // create
        val horizontalPanel = Window("", skin, Names.Panel.HORIZONTAL_PANEL)
        val table = Table()
        val btnBack = Button(skin, Names.Button.EXIT)  // TODO: create back-button

        scrollPane = ScrollPane(table, skin)
        tabsGroup = ButtonGroup()
        horizontalFilterGroup = HorizontalFilterGroup("Filter", 0.01f, skin)

        // adjust
        Adjuster.adjustHorizontalMenu(horizontalPanel, scrollPane)
        scrollPane.color = Colors.LIGHT_GRAY
        scrollPane.fadeScrollBars = false
        table.left().top()

        // link
        Linker.linkMenuBackButton(btnBack)

        // add
        StageAdder.addTo(this, horizontalPanel, Vector2(-0.287f, 0.075f), Vector2(1.573f, 0.724f), MenuFloating.CENTER)
        TableAdder.addScrollButtonsTo(horizontalPanel, scrollUpButton, 0.04f, 0.0625f, -0.00174f, -0.00174f, true)
        TableAdder.addScrollPanelTo(horizontalPanel, scrollPane, 1.573f, 0.618f)
        TableAdder.addScrollButtonsTo(horizontalPanel, scrollDownButton, 0.04f, 0.0625f, -0.00174f, -0.00174f, false)
        StageAdder.addTo(this, btnBack, Vector2(-0.354f, 0.868f), Dimensions.Button.BACK, MenuFloating.CENTER)

        Gdx.input.inputProcessor = this // to take input from ui
        scrollFocus = scrollPane
    }

    override fun act(deltaTime: Float) {
        super.act(deltaTime)

        if (scrollUpButton.isPressed) {
            scrollPane.scrollPercentY = scrollPane.scrollPercentY - Velocities.SCROLLPANE_SCROLL_FACTOR * deltaTime
        }

        if (scrollDownButton.isPressed) {
            scrollPane.scrollPercentY = scrollPane.scrollPercentY + Velocities.SCROLLPANE_SCROLL_FACTOR * deltaTime
        }
    }

    fun addButton(button: Button) {
        if (numberOfButtons >= Constraints.MAX_NUMBER_MENU_BUTTONS) {
            throw IndexOutOfBoundsException(Errors.TOO_MANY_HORIZONTAL_MENU_BUTTONS)
        }

        val menuButtonDim = Dimensions.Button.MENU
        val menuFloatingCenter = MenuFloating.CENTER

        when (numberOfButtons) {
            0 -> StageAdder.addTo(this, button, Vector2(-0.269f, 0.839f), menuButtonDim, menuFloatingCenter)
            1 -> StageAdder.addTo(this, button, Vector2(0.134f, 0.839f), menuButtonDim, menuFloatingCenter)
            2 -> StageAdder.addTo(this, button, Vector2(0.536f, 0.839f), menuButtonDim, menuFloatingCenter)
            else -> StageAdder.addTo(this, button, Vector2(0.939f, 0.839f), menuButtonDim, menuFloatingCenter)
        }

        numberOfButtons++
    }

    fun addTab(button: Button) {
        if (numberOfTabs >= Constraints.MAX_NUMBER_MENU_TABS) {
            throw IndexOutOfBoundsException(Errors.TOO_MANY_MENU_TABS)
        }

        val yPos = 0.024f

        when (numberOfTabs) {
            0 -> StageAdder.addTo(this, button, Vector2(-0.288f, yPos), Dimensions.Button.TAB, MenuFloating.CENTER)
            1 -> StageAdder.addTo(this, button, Vector2(-0.056f, yPos), Dimensions.Button.TAB, MenuFloating.CENTER)
            2 -> StageAdder.addTo(this, button, Vector2(0.177f, yPos), Dimensions.Button.TAB, MenuFloating.CENTER)
            3 -> StageAdder.addTo(this, button, Vector2(0.410f, yPos), Dimensions.Button.TAB, MenuFloating.CENTER)
        }

        tabsGroup.add(button)
        numberOfTabs++
    }

    fun addFilter(button: Button) {
        horizontalFilterGroup.addButton(button, 0.0052f)
    }

    fun addFilterAll(button: Button) {
        horizontalFilterGroup.addButton(button, 0.014f)
    }

    fun placeFilter(position: Vector2, menuFloating: MenuFloating) {
        StageAdder.addTo(horizontalFilterGroup, this, position, menuFloating)
    }
}