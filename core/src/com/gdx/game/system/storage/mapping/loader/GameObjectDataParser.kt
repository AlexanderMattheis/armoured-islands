package com.gdx.game.system.storage.mapping.loader

import com.gdx.game.system.defaults.views.world.MapFormatStructure
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.ANIMATED
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.BLOCKED
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.DRAGGABLE
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.INDEX
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.LIVES
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.MIRRORED
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.NAME
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.PLAYABLE
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.POINTS
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.ROTATION
import com.gdx.game.system.defaults.views.world.MapFormatStructure.ObjectTagAttributes.TIMES
import com.gdx.game.system.defaults.views.world.objects.CharacterDefaults
import com.gdx.game.system.defaults.views.world.objects.ItemDefaults
import com.gdx.game.system.defaults.views.world.objects.ObstacleDefaults
import com.gdx.game.system.defaults.views.world.objects.SurfaceDefaults
import com.gdx.game.system.storage.mapping.MapValueParser
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import com.gdx.game.views.world.objects.elements.actions.Action
import com.gdx.game.views.world.objects.elements.references.TextureReference
import com.gdx.game.views.world.objects.types.Character
import com.gdx.game.views.world.objects.types.Item
import com.gdx.game.views.world.objects.types.Obstacle
import com.gdx.game.views.world.objects.types.Surface
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.util.*
import java.util.logging.Logger

class GameObjectDataParser : DataParser {
    companion object {
        private val LOGGER = Logger.getLogger(GameObjectDataParser::class.qualifiedName)
    }

    override fun getData(xmlDocument: Document): Data {
        val objectsList = xmlDocument.getElementsByTagName(MapFormatStructure.Tags.OBJECTS)

        val characters: MutableList<GameObject> = ArrayList()
        val items: MutableList<GameObject> = ArrayList()
        val obstacles: MutableList<GameObject> = ArrayList()
        val surfaces: MutableList<GameObject> = ArrayList()

        for (i in 0 until objectsList.length) {
            val objects = objectsList.item(i)
            val attributes = objects.attributes

            val typeName = attributes.getNamedItem(MapFormatStructure.ObjectsTagAttributes.TYPE).nodeValue

            when (ObjectType.getTypeByName(typeName)) {
                ObjectType.CHARACTERS -> characters.addAll(getCharacters(objects))
                ObjectType.ITEMS -> items.addAll(getItems(objects))
                ObjectType.OBSTACLES -> obstacles.addAll(getObstacles(objects))
                ObjectType.SURFACES, ObjectType.SURFACES_TRANSITIONS -> surfaces.addAll(getSurfaces(objects, typeName))
            }
        }

        return GameObjectData(characters, items, obstacles, surfaces)
    }

    private fun getCharacters(objectsNode: Node): List<GameObject> {
        val objectList = objectsNode.childNodes
        val characters: MutableList<GameObject> = ArrayList()
        val parser = MapValueParser()

        for (i in 0 until objectList.length) {
            val node = objectList.item(i)

            if (node.nodeType != Node.ELEMENT_NODE) {
                continue
            }

            val attributes = node.attributes
            val actionType = parser.getActionType(attributes, CharacterDefaults.ACTION_TYPE)
            val actionVariable = parser.getActionVariable(attributes, CharacterDefaults.ACTION_VARIABLE)
            val animated = parser.getBoolean(attributes, ANIMATED, CharacterDefaults.ANIMATED)
            val index = parser.getInteger(attributes, INDEX, CharacterDefaults.INDEX)
            val lives = parser.getInteger(attributes, LIVES, CharacterDefaults.LIVES)
            val movement = parser.getMovement(attributes, CharacterDefaults.MOVEMENT)
            val name = parser.getName(attributes, NAME, "")
            val playable = parser.getBoolean(attributes, PLAYABLE, CharacterDefaults.PLAYABLE)
            val points = parser.getInteger(attributes, POINTS, CharacterDefaults.POINTS)
            val position = parser.getPosition(attributes, CharacterDefaults.POSITION)
            val rotation = parser.getInteger(attributes, ROTATION, CharacterDefaults.ROTATION)
            val times = parser.getInteger(attributes, TIMES, CharacterDefaults.TIMES)
            val textureReference = TextureReference(name, index, animated)
            val character = Character(textureReference, null, position, rotation, movement)

            character.action = Action(actionType, actionVariable, times)
            character.lives = lives
            character.maxLives = lives
            character.isPlayable = playable
            character.points = points

            characters.add(character)
        }
        return characters
    }

    private fun getItems(objectsNode: Node): List<GameObject> {
        val objectList = objectsNode.childNodes
        val items: MutableList<GameObject> = ArrayList()
        val parser = MapValueParser()

        for (i in 0 until objectList.length) {
            val node = objectList.item(i)

            if (node.nodeType != Node.ELEMENT_NODE) {
                continue
            }

            val attributes = node.attributes
            val actionType = parser.getActionType(attributes, ItemDefaults.ACTION_TYPE)
            val actionVariable = parser.getActionVariable(attributes, ItemDefaults.ACTION_VARIABLE)
            val animated = parser.getBoolean(attributes, ANIMATED, ItemDefaults.ANIMATED)
            val draggable = parser.getBoolean(attributes, DRAGGABLE, ItemDefaults.DRAGGABLE)
            val index = parser.getInteger(attributes, INDEX, ItemDefaults.INDEX)
            val movement = parser.getMovement(attributes, ItemDefaults.MOVEMENT)
            val name = parser.getName(attributes, NAME, "")
            val position = parser.getPosition(attributes, ItemDefaults.POSITION)
            val rotation = parser.getInteger(attributes, ROTATION, ItemDefaults.ROTATION)
            val times = parser.getInteger(attributes, TIMES, ItemDefaults.TIMES)
            val textureReference = TextureReference(name, index, animated)
            val item = Item(textureReference, null, position)

            item.action = Action(actionType, actionVariable, times)
            item.isDraggable = draggable
            item.movement = movement
            item.rotation = rotation

            items.add(item)
        }
        return items
    }

    private fun getObstacles(objectsNode: Node): List<GameObject> {
        val objectList = objectsNode.childNodes
        val obstacles: MutableList<GameObject> = ArrayList()
        val parser = MapValueParser()

        for (i in 0 until objectList.length) {
            val node = objectList.item(i)

            if (node.nodeType != Node.ELEMENT_NODE) {
                continue
            }

            val attributes = node.attributes
            val actionType = parser.getActionType(attributes, ObstacleDefaults.ACTION_TYPE)
            val actionVariable = parser.getActionVariable(attributes, ObstacleDefaults.ACTION_VARIABLE)
            val blocked = parser.getBoolean(attributes, BLOCKED, ObstacleDefaults.BLOCKED)
            val animated = parser.getBoolean(attributes, ANIMATED, ObstacleDefaults.ANIMATED)
            val index = parser.getInteger(attributes, INDEX, ItemDefaults.INDEX)
            val movement = parser.getMovement(attributes, ObstacleDefaults.MOVEMENT)
            val name = parser.getName(attributes, NAME, "")
            val position = parser.getPosition(attributes, ObstacleDefaults.POSITION)
            val rotation = parser.getInteger(attributes, ROTATION, ObstacleDefaults.ROTATION)
            val times = parser.getInteger(attributes, TIMES, ObstacleDefaults.ROTATION)
            val toPosition = parser.getToPosition(attributes, ObstacleDefaults.POSITION)
            val textureReference = TextureReference(name, index, animated)
            val obstacle = Obstacle(textureReference, null, position, rotation)

            obstacle.action = Action(actionType, actionVariable, times)
            obstacle.isBlocked = blocked
            obstacle.movement = movement
            obstacle.toPosition = toPosition

            obstacles.add(obstacle)
        }
        return obstacles
    }

    private fun getSurfaces(objectsNode: Node, type: String): List<GameObject> {
        val objectList = objectsNode.childNodes
        val surfaces: MutableList<GameObject> = ArrayList()
        val parser = MapValueParser()

        for (i in 0 until objectList.length) {
            val node = objectList.item(i)

            if (node.nodeType != Node.ELEMENT_NODE) {
                continue
            }

            val attributes = node.attributes
            val actionType = parser.getActionType(attributes, SurfaceDefaults.ACTION_TYPE)
            val actionVariable = parser.getActionVariable(attributes, SurfaceDefaults.ACTION_VARIABLE)
            val blocked = parser.getBoolean(attributes, BLOCKED, SurfaceDefaults.BLOCKED)
            val animated = parser.getBoolean(attributes, ANIMATED, SurfaceDefaults.ANIMATED)
            val index = parser.getInteger(attributes, INDEX, ItemDefaults.INDEX)
            val mirrored = parser.getBoolean(attributes, MIRRORED, SurfaceDefaults.MIRRORED)
            val movement = parser.getMovement(attributes, SurfaceDefaults.MOVEMENT)
            val name = parser.getName(attributes, NAME, type)
            val position = parser.getPosition(attributes, SurfaceDefaults.POSITION)
            val rotation = parser.getInteger(attributes, ROTATION, SurfaceDefaults.ROTATION)
            val times = parser.getInteger(attributes, TIMES, SurfaceDefaults.ROTATION)
            val textureReference = TextureReference(name, index, animated)
            val surface = Surface(textureReference, null, position, rotation, mirrored)

            surface.action = Action(actionType, actionVariable, times)
            surface.isBlocked = blocked
            surface.movement = movement

            surfaces.add(surface)
        }
        return surfaces
    }
}