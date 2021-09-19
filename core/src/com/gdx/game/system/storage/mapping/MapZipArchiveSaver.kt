package com.gdx.game.system.storage.mapping

import com.gdx.game.system.defaults.FileExtensions
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.views.world.MapArchiveDefaults
import com.gdx.game.system.defaults.views.world.MapFormatStructure
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.storage.mapping.saver.GameObjectDataBuilder
import com.gdx.game.system.storage.mapping.saver.MapMetaDataBuilder
import com.gdx.game.views.world.Map
import org.w3c.dom.Document
import java.io.*
import java.nio.file.Files
import java.util.logging.Level
import java.util.logging.Logger
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


object MapZipArchiveSaver {
    private val LOGGER = Logger.getLogger(MapZipArchiveSaver::class.qualifiedName)
    
    fun save(mapsPath: String, map: Map) {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()

        try {
            val document = documentBuilderFactory.newDocumentBuilder().newDocument()
            document.appendChild(document.createElement(MapFormatStructure.Tags.MAP))
            storeInXmlTree(map, document)
            writeToZipFile(document, mapsPath + Paths.PATHS_SEPARATOR + map.name + FileExtensions.MAP_ARCHIVE)
        } catch (e: ParserConfigurationException) {
            LOGGER.log(Level.SEVERE, Errors.CREATING_MAPZ, e)
        }
    }

    private fun storeInXmlTree(map: Map, xmlDocument: Document) {
        val metaDataBuilder = MapMetaDataBuilder()
        val gameObjectDataBuilder = GameObjectDataBuilder()

        xmlDocument.xmlStandalone = true

        metaDataBuilder.storeToXmlTree(map, xmlDocument)
        gameObjectDataBuilder.storeToXmlTree(map, xmlDocument)
    }

    private fun writeToZipFile(document: Document, filePath: String) {
        try {
            val zipOutputStream = ZipOutputStream(Files.newOutputStream(File(filePath).toPath()))
            val mapEntry = ZipEntry(MapArchiveDefaults.MAP_NAME)

            zipOutputStream.putNextEntry(mapEntry)
            writeXML(document, zipOutputStream)
            zipOutputStream.closeEntry()
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.CREATING_XML, e)
        }
    }

    private fun writeXML(document: Document, outputStream: OutputStream) {
        try {
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.METHOD, "xml")
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "")  // to create a new line after the declaration
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")

            transformer.transform(DOMSource(document), StreamResult(OutputStreamWriter(outputStream, "UTF-8")))
        } catch (e: TransformerException) {
            LOGGER.log(Level.SEVERE, Errors.CONVERTING_XML_TREE_TO_FILE, e)
        } catch (e: FileNotFoundException) {
            LOGGER.log(Level.SEVERE, Errors.PATH_NOT_READABLE, e)
        }
    }
}