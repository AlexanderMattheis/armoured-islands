package com.gdx.game.views.screens.stages.huds.parts

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions.Button.FILE
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions.Button.FOLDER
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.views.screens.compositing.Creator
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.huds.EditorHud
import com.gdx.game.views.screens.widgets.Paddings
import com.gdx.game.views.world.editor.MapEditor
import com.gdx.game.views.world.objects.ObjectType
import com.badlogic.gdx.utils.Array as GdxArray

class TypeObjectsPanel(skin: Skin, parent: EditorHud) : Table() {

    init {
        // create
        val lblType = Label("Type", skin)
        val lblObject = Label("Object", skin)
        val objectButtons = createObjectButtons(skin, parent)
        val itbCharacters = ImageTextButton("Characters", skin, Names.Button.FOLDER)
        val itbItems = ImageTextButton("Items", skin, Names.Button.FOLDER)
        val itbObstacles = ImageTextButton("Obstacles", skin, Names.Button.FOLDER)
        val itbSurfaces = ImageTextButton("Surfaces", skin, Names.Button.FOLDER)
        val itbDecor = ImageTextButton("Decor", skin, Names.Button.FOLDER)
        val typeButtonsGroup = ButtonGroup<Button>(itbCharacters, itbItems, itbObstacles, itbSurfaces, itbDecor)

        val typeButtonsTable = Table()
        val objectButtonsTable = Table()

        // adjust
        itbCharacters.isChecked = true
        Proportioner.setFontSizes(arrayOf(itbCharacters, itbItems, itbObstacles, itbSurfaces, itbDecor), 0.000399f)

        Creator.createFileButtonImages(objectButtons, parent.characterAtlas, 0.0434f)
        Creator.createFolderImage(itbCharacters, parent.characterAtlas, 0.0486f)
        Creator.createFolderImage(itbItems, parent.itemsAtlas, 0.0486f)
        Creator.createFolderImage(itbObstacles, parent.obstaclesAtlas, 0.0486f)
        Creator.createFolderImage(itbSurfaces, parent.surfacesAtlas, 0.0486f)

        // link
        linkTypeButtons(typeButtonsGroup, arrayOf(itbCharacters, itbItems, itbObstacles, itbSurfaces, itbDecor), objectButtons, parent)

        // add types
        TableAdder.addRowTo(this, 0.0451f, 5)
        TableAdder.addTo(this, lblType, Paddings(0f, 0f, 0f, -0.1f), 1, true)
        TableAdder.addTo(typeButtonsTable, itbCharacters, FOLDER, Paddings(0.00521f, 0f, 0f, 0f))
        TableAdder.addTo(typeButtonsTable, itbItems, FOLDER, Paddings(0.00521f, 0f, 0f, 0f))
        TableAdder.addTo(typeButtonsTable, itbObstacles, FOLDER, Paddings(0.00521f, 0f, 0f, 0f))
        TableAdder.addTo(typeButtonsTable, itbSurfaces, FOLDER, Paddings(0.00521f, 0f, 0f, 0f))
        TableAdder.addTo(typeButtonsTable, itbDecor, FOLDER, Paddings(0.00521f, 0f, 0f, 0f))
        TableAdder.addTo(this, typeButtonsTable, 5, true)

        // add objects
        TableAdder.addRowTo(this, 0.0521f, 5)
        TableAdder.addTo(this, lblObject, Paddings(0f, 0f, 0f, -0.08f), true)

        var currentButtonIndex = 0

        for (i in 0..4) {
            for (j in 0..4) {
                TableAdder.addTo(objectButtonsTable, objectButtons[currentButtonIndex], FILE)
                currentButtonIndex++
            }
            TableAdder.addRowTo(objectButtonsTable)
        }

        TableAdder.addTo(this, objectButtonsTable, Paddings(0.00868f, 0f, 0f, 0f), 5)
    }

    private fun createObjectButtons(skin: Skin, parent: EditorHud): List<ImageButton> {
        val buttonGroup = ButtonGroup<Button>()
        val buttons: MutableList<ImageButton> = ArrayList()

        for (i in 0..4) {
            for (j in 0..4) {
                val ibtObject = ImageButton(skin, Names.Button.FILE)
                buttonGroup.add(ibtObject)
                buttons.add(ibtObject)
            }
        }

        // link
        linkObjectButtons(buttonGroup, buttons, parent)
        return buttons
    }

    private fun linkTypeButtons(typeButtonsGroup: ButtonGroup<*>, buttons: Array<ImageTextButton>,
                                imageButtons: List<ImageButton>, parent: EditorHud) {
        for (button in buttons) {
            linkTypeButton(typeButtonsGroup, button, parent, imageButtons)
        }
    }

    private fun linkTypeButton(typeButtonsGroup: ButtonGroup<*>, button: Button, parent: EditorHud, imageButtons: List<ImageButton>) {
        button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                imageButtons[0].isChecked = true  // to reset the checked file after folder switch
                val editor = parent.editor

                when (typeButtonsGroup.checkedIndex) {
                    -1 -> { }
                    0 -> createFileButtonImages(ObjectType.CHARACTERS, imageButtons, editor, parent.characterAtlas)
                    1 -> createFileButtonImages(ObjectType.ITEMS, imageButtons, editor, parent.itemsAtlas)
                    2 -> createFileButtonImages(ObjectType.OBSTACLES, imageButtons, editor, parent.obstaclesAtlas)
                    3 -> createFileButtonImages(ObjectType.SURFACES, imageButtons, editor, parent.surfacesAtlas)
                    4 -> { }
                    else -> throw IllegalStateException("Unexpected value: " + typeButtonsGroup.checkedIndex)
                }
            }
        })
    }

    private fun createFileButtonImages(objectType: ObjectType, imageButtons: List<ImageButton>, editor: MapEditor, atlas: TextureAtlas?) {
        if (atlas != null) {
            editor.setSelectedTypeAndObject(objectType, atlas.regions[0].name)
            Creator.createFileButtonImages(imageButtons, atlas, 0.0434f)
        }
    }

    private fun linkObjectButtons(objectButtonsGroup: ButtonGroup<*>, buttons: List<ImageButton>, parent: EditorHud) {
        for (button in buttons) {
            linkObjectButton(button, objectButtonsGroup, parent)
        }
    }

    private fun linkObjectButton(button: ImageButton, objectButtonsGroup: ButtonGroup<*>, parent: EditorHud) {
        button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                val checkedIndex = objectButtonsGroup.checkedIndex
                val editor = parent.editor

                when (editor.selectedType) {
                    ObjectType.CHARACTERS -> setObjectName(editor, parent.characterAtlas, checkedIndex)
                    ObjectType.ITEMS -> setObjectName(editor, parent.itemsAtlas, checkedIndex)
                    ObjectType.OBSTACLES -> setObjectName(editor, parent.obstaclesAtlas, checkedIndex)
                    ObjectType.SURFACES -> setObjectName(editor, parent.surfacesAtlas, checkedIndex)
                    else -> { }
                }
            }
        })
    }

    private fun setObjectName(editor: MapEditor, atlas: TextureAtlas?, index: Int) {
        if (atlas != null) {
            editor.objectName = getRegionName(getUniqueNameRegions(atlas.regions), index)
        }
    }

    private fun getUniqueNameRegions(regions: GdxArray<AtlasRegion>): GdxArray<AtlasRegion> {
        val uniqueNameRegions = GdxArray<AtlasRegion>()
        val textureNames: MutableSet<String> = HashSet()

        for (region in regions) {
            if (region.name !in textureNames) {
                textureNames.add(region.name)
                uniqueNameRegions.add(region)
            }
        }

        return uniqueNameRegions
    }

    private fun getRegionName(regions: GdxArray<AtlasRegion>, checkedIndex: Int): String {
        return if (checkedIndex == -1 || checkedIndex >= regions.size) ""  else regions[checkedIndex].name
    }
}