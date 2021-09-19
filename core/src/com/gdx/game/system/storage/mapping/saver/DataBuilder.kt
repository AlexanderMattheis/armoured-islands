package com.gdx.game.system.storage.mapping.saver

import com.gdx.game.views.world.Map
import org.w3c.dom.Document

interface DataBuilder {
    fun storeToXmlTree(map: Map, xmlDocument: Document)
}