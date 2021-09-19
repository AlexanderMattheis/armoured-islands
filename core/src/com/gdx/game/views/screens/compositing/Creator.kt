package com.gdx.game.views.screens.compositing

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.utils.Scaling
import com.gdx.game.Main.Companion.windowHeightPixel
import com.gdx.game.system.defaults.Colors
import com.gdx.game.system.defaults.views.screens.widgets.Fonts.INPUT
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Names
import java.util.*

object Creator {
    fun createMenuFileButton(label: String?, skin: Skin?): ImageTextButton {
        val imageTextButton = ImageTextButton(label, skin, Names.Button.MENU_FILE)
        val buttonImage = imageTextButton.image
        val buttonLabel = imageTextButton.label

        imageTextButton.clearChildren()
        imageTextButton.add(buttonImage).row()
        imageTextButton.add(buttonLabel).padTop(-0.101f * windowHeightPixel).row()

        return imageTextButton
    }

    fun createFilterFontStyle(skin: Skin): TextButtonStyle {
        val textButtonStyle = TextButtonStyle()

        textButtonStyle.font = skin.getFont(INPUT)
        textButtonStyle.font.setUseIntegerPositions(false)
        textButtonStyle.font.data.setScale(0.000521f * windowHeightPixel)
        textButtonStyle.fontColor = Colors.GRAY
        textButtonStyle.checkedFontColor = Colors.RED
        textButtonStyle.overFontColor = Colors.LIGHT_RED
        textButtonStyle.disabledFontColor = Colors.GRAY

        return textButtonStyle
    }

    fun createFolderImage(button: ImageTextButton, textureAtlas: TextureAtlas?, size: Float) {
        val textureRegions = if (textureAtlas != null) textureAtlas.regions else throw NullPointerException(Errors.ATLAS_NULL)

        if (textureRegions.size > 0) {
            val textureRegion = textureRegions[0]
            val label = button.label
            val image = Image(textureRegion)

            image.setScaling(Scaling.fit)
            button.clearChildren()
            button.add(image).width(size * windowHeightPixel).height(size * windowHeightPixel).padTop(5f).center().row()
            button.add().row()
            button.add(label).padBottom(1f)
        }
    }

    fun createFileButtonImages(imageButtons: List<ImageButton>, textureAtlas: TextureAtlas?, size: Float) {
        val textureRegions = if (textureAtlas != null) textureAtlas.regions else throw NullPointerException(Errors.ATLAS_NULL)

        for (button in imageButtons) {
            button.reset()
        }

        val textureNames: MutableSet<String> = HashSet()
        var buttonIndex = 0

        for (i in 0 until textureRegions.size) {
            val textureRegion = textureRegions[i]

            // to show each object once in the list (each object can have multiple textures due to animations)
            if (textureNames.contains(textureRegion.name)) {
                continue
            }

            textureNames.add(textureRegion.name)

            if (buttonIndex < imageButtons.size) {
                val imageButton = imageButtons[buttonIndex]
                val image = Image(textureRegion)

                image.setScaling(Scaling.fit)
                imageButton.add(image).width(size * windowHeightPixel).height(size * windowHeightPixel).center()
                buttonIndex++
            }
        }
    }
}