package com.gdx.game.system.storage.mapping

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gdx.game.system.defaults.messages.errors.Errors.Map.BUILDING_MAP
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.Paths.PATHS_SEPARATOR
import com.gdx.game.system.defaults.views.world.MapArchiveDefaults
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.storage.mapping.archives.ZipExtractor
import com.gdx.game.system.storage.mapping.loader.GameObjectData
import com.gdx.game.system.storage.mapping.loader.GameObjectDataParser
import com.gdx.game.system.storage.mapping.loader.MapMetaData
import com.gdx.game.system.storage.mapping.loader.MapMetaDataParser
import com.gdx.game.views.world.Map
import org.w3c.dom.Document
import org.xml.sax.SAXException
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

object MapZipArchiveLoader {
    private val LOGGER = Logger.getLogger(MapZipArchiveLoader::class.qualifiedName)

    fun load(mapZipArchivePath: String): MapZipArchive {
        val zipExtractor = ZipExtractor()

        // extract
        try {
            zipExtractor.extractIfNecessary(mapZipArchivePath, Paths.Temp.Maps.LOADING)
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.Map.MAP_NOT_LOADED, e)
        }

        // load
        var map: Map? = null
        var image: Image? = null

        try {
            map = getMap(File(Paths.Temp.Maps.LOADING + PATHS_SEPARATOR + MapArchiveDefaults.MAP_NAME))
        } catch (e: FileNotFoundException) {
            LOGGER.log(Level.SEVERE, Errors.Map.MAP_NOT_LOADED, e)
        }

        try {
            image = getImage(File(Paths.Temp.Maps.LOADING + PATHS_SEPARATOR + MapArchiveDefaults.THUMBNAIL_NAME))
        } catch (e: FileNotFoundException) {
            LOGGER.log(Level.WARNING, Errors.Map.MAP_THUMBNAIL_NOT_LOADED, e)
        }

        return MapZipArchive(map, image)
    }

    private fun getMap(file: File): Map {
        if (!file.exists()) {
            throw FileNotFoundException(Errors.NOT_EXISTENT_FILE + file.name)
        }

        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        // against XXE-Attack https://stackoverflow.com/questions/40649152/how-to-prevent-xxe-attack
        documentBuilderFactory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true) // limits the number of XML constructs
        documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "") // restricts access to DTDs' on the specified protocols

        try {
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val document = documentBuilder.parse(file)
            // removes redundancies:
            // - comments
            // - white space outside root/document tags
            // - white space between an attribute name and "="
            // - white space between attributes
            // ...
            document.documentElement.normalize()
            return buildMap(document)
        } catch (e: ParserConfigurationException) {
            LOGGER.log(Level.SEVERE, Errors.WRONG_CONFIG, e)
        } catch (e: SAXException) {
            LOGGER.log(Level.SEVERE, Errors.PARSING_XML, e)
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.READING_FILE, e)
        }

        throw NullPointerException(BUILDING_MAP)
    }

    private fun buildMap(xmlDocument: Document): Map {
        val metaDataParser = MapMetaDataParser()
        val gameObjectDataParser = GameObjectDataParser()

        val map = initMap(metaDataParser.getData(xmlDocument) as MapMetaData)
        fillMap(map, gameObjectDataParser.getData(xmlDocument) as GameObjectData)
        return map
    }

    private fun initMap(mapMetaData: MapMetaData): Map {
        return Map(mapMetaData.name, mapMetaData.dimensions)
    }

    private fun fillMap(map: Map, gameObjectData: GameObjectData) {
        map.setCharacters(gameObjectData.characters)
        map.setItems(gameObjectData.items)
        map.setObstacles(gameObjectData.obstacles)
        map.setSurfaces(gameObjectData.surfaces)
    }

    private fun getImage(file: File): Image {
        if (!file.exists()) {
            throw FileNotFoundException(Errors.NOT_EXISTENT_FILE + file.name)
        }

        val texture = Texture(FileHandle(file.absolutePath))

        require(texture.width.toFloat() == MapArchiveDefaults.THUMBNAIL_DIMENSION.x && texture.height.toFloat() == MapArchiveDefaults.THUMBNAIL_DIMENSION.y) { Errors.Map.THUMBNAIL_NOT_RIGHT_SIZE }
        return Image(texture)
    }
}