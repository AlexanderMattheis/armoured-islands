package com.gdx.game.system.storage.mapping.loader

import com.gdx.game.system.defaults.Regex.POSITIVE_INTEGER_OR_ZERO
import com.gdx.game.system.defaults.views.world.MapFormatStructure
import com.gdx.game.system.defaults.messages.errors.Errors
import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import java.util.logging.Level
import java.util.logging.Logger

class MapMetaDataParser : DataParser {
    companion object {
        private val LOGGER = Logger.getLogger(MapMetaDataParser::class.qualifiedName)
    }

    override fun getData(xmlDocument: Document): Data {
        val nodes = xmlDocument.getElementsByTagName(MapFormatStructure.Tags.MAP)

        if (nodes.length != 1) {
            LOGGER.log(Level.SEVERE, Errors.Map.COUNT_MAP_ROOT)
            return MapMetaData("", 0, 0)
        }

        val mapTag = nodes.item(0)
        val mapTagAttributes = mapTag.attributes

        val name = mapTagAttributes.getNamedItem(MapFormatStructure.MapTagAttributes.NAME).nodeValue
        val width = getDimension(mapTagAttributes, MapFormatStructure.MapTagAttributes.WIDTH)
        val height = getDimension(mapTagAttributes, MapFormatStructure.MapTagAttributes.HEIGHT)

        return MapMetaData(name, width, height)
    }

    private fun getDimension(mapTagAttributes: NamedNodeMap, attribute: String): Int {
        val attributeValue = mapTagAttributes.getNamedItem(attribute).nodeValue
        val isPositiveNumber = attributeValue.matches(POSITIVE_INTEGER_OR_ZERO)
        return if (isPositiveNumber) attributeValue.toInt() else 0
    }
}