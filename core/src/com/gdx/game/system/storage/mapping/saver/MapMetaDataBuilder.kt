package com.gdx.game.system.storage.mapping.saver

import com.gdx.game.system.defaults.views.world.MapFormatStructure
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.views.world.Map
import org.w3c.dom.Document
import org.w3c.dom.Element

class MapMetaDataBuilder : DataBuilder {

    override fun storeToXmlTree(map: Map, xmlDocument: Document) {
        val mapTagElements = xmlDocument.getElementsByTagName(MapFormatStructure.Tags.MAP)

        if (mapTagElements.length == 1) {
            val root = mapTagElements.item(0) as Element
            root.setAttribute(MapFormatStructure.MapTagAttributes.NAME, map.name)

            val mapDimensions = map.getDimensions()
            root.setAttribute(MapFormatStructure.MapTagAttributes.WIDTH, mapDimensions.x.toInt().toString())
            root.setAttribute(MapFormatStructure.MapTagAttributes.HEIGHT, mapDimensions.y.toInt().toString())
        } else {
            throw IllegalArgumentException(Errors.WRONG_NUMBER_ROOT_NODES)
        }
    }
}