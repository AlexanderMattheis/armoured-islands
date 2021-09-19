package com.gdx.game.system.storage.mapping.loader

import org.w3c.dom.Document

interface DataParser {
    fun getData(xmlDocument: Document): Data
}