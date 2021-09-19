package com.gdx.game.system.storage.mapping

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.messages.errors.Errors.WRONG_NUMBER_PATHS
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.Regex
import com.gdx.game.system.defaults.views.world.MapFormatStructure
import com.gdx.game.views.world.objects.elements.actions.ActionType
import com.gdx.game.views.world.objects.elements.actions.ActionVariable
import com.gdx.game.views.world.objects.elements.actions.Movement
import org.w3c.dom.NamedNodeMap

class MapValueParser {
    fun getBoolean(attributes: NamedNodeMap, attributeName: String, defaultValue: Boolean): Boolean {
        val attribute = attributes.getNamedItem(attributeName)

        if (attribute != null) {
            val value = attribute.nodeValue
            return if (value == "true" || value == "false") value.toBoolean() else defaultValue
        }

        return defaultValue
    }

    fun getInteger(attributes: NamedNodeMap, attributeName: String, defaultValue: Int): Int {
        val attribute = attributes.getNamedItem(attributeName)

        if (attribute != null) {
            val value = attribute.nodeValue
            return if (value.matches(Regex.INTEGER)) value.toInt() else defaultValue
        }

        return defaultValue
    }

    fun getName(attributes: NamedNodeMap, attributeName: String?, type: String): String {
        val attribute = attributes.getNamedItem(attributeName)
        val separator = Paths.PATHS_SEPARATOR
        val pathParts = type.split(separator)

        if (type.contains("transitions") && pathParts.size != 3) {
            throw IllegalArgumentException(WRONG_NUMBER_PATHS)
        }

        return if (attribute != null) {
            when {
                type.contains("transitions") -> pathParts[1] + separator + pathParts[2] + separator + attribute.nodeValue
                else -> attribute.nodeValue
            }
        } else ""
    }

    fun getActionType(attributes: NamedNodeMap, defaultValue: ActionType): ActionType {
        val attribute = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.ACTION_TYPE)
        return if (attribute != null) ActionType.getTypeByName(attribute.nodeValue) else defaultValue
    }

    fun getActionVariable(attributes: NamedNodeMap, defaultValue: ActionVariable): ActionVariable {
        val attribute = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.ACTION_VARIABLE)
        return if (attribute != null) ActionVariable.getTypeByName(attribute.nodeValue) else defaultValue
    }

    fun getMovement(attributes: NamedNodeMap, defaultValue: Movement): Movement {
        val attribute = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.MOVEMENT)
        return if (attribute != null) Movement.getTypeByName(attribute.nodeValue) else defaultValue
    }

    fun getPosition(attributes: NamedNodeMap, defaultValue: Vector2): Vector2 {
        val attributeX = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.X)
        val attributeY = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.Y)

        if (attributeX != null && attributeY != null) {
            val xString = attributeX.nodeValue
            val yString = attributeY.nodeValue

            return getNumericalVector(xString, yString, defaultValue)
        }

        return defaultValue
    }

    private fun getNumericalVector(xString: String, yString: String, defaultValue: Vector2): Vector2 {
        if (xString.matches(Regex.INTEGER) && yString.matches(Regex.INTEGER)) {
            val x = xString.toInt()  // 'floor()'
            val y = yString.toInt()

            return Vector2(x.toFloat(), y.toFloat())
        }
        return defaultValue
    }

    /**
     * The position to which a character is teleported.
     */
    fun getToPosition(attributes: NamedNodeMap, defaultValue: Vector2): Vector2 {
        val attributeToX = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.TO_X)
        val attributeToY = attributes.getNamedItem(MapFormatStructure.ObjectTagAttributes.TO_Y)

        if (attributeToX != null && attributeToY != null) {
            val xString = attributeToX.nodeValue
            val yString = attributeToY.nodeValue
            return getNumericalVector(xString, yString, defaultValue)
        }

        return defaultValue
    }
}