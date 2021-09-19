package com.gdx.game.system.storage.mapping.saver

import com.gdx.game.system.defaults.Paths.PATHS_SEPARATOR
import com.gdx.game.system.defaults.views.world.MapFormatStructure
import com.gdx.game.system.defaults.views.world.objects.*
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.ObjectType
import com.gdx.game.views.world.objects.types.Character
import com.gdx.game.views.world.objects.types.Item
import com.gdx.game.views.world.objects.types.Obstacle
import com.gdx.game.views.world.objects.types.Surface
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.util.*

class GameObjectDataBuilder : DataBuilder {
    override fun storeToXmlTree(map: com.gdx.game.views.world.Map, xmlDocument: Document) {
        val mapTagElements = xmlDocument.getElementsByTagName(MapFormatStructure.Tags.MAP)

        if (mapTagElements.length == 1) {
            val root = mapTagElements.item(0) as Element

            root.appendChild(getObjectsXmlTree(map.getCharacters(), ObjectType.CHARACTERS.objectType, xmlDocument))
            root.appendChild(getObjectsXmlTree(map.getItems(), ObjectType.ITEMS.objectType, xmlDocument))
            root.appendChild(getObjectsXmlTree(map.getObstacles(), ObjectType.OBSTACLES.objectType, xmlDocument))

            getSurfacesXmlTrees(map, xmlDocument).forEach { newChild: Element -> root.appendChild(newChild) }
        } else {
            throw IllegalArgumentException(Errors.WRONG_NUMBER_ROOT_NODES)
        }
    }

    private fun getSurfacesXmlTrees(map: com.gdx.game.views.world.Map, xmlDocument: Document): List<Element> {
        val surfacesByTypeNames = getSurfacesByTypeNames(map)
        val typeNames = surfacesByTypeNames.keys
        val elements: MutableList<Element> = ArrayList()

        for (typeName in typeNames) {
            elements.add(getObjectsXmlTree(surfacesByTypeNames[typeName], typeName, xmlDocument))
        }

        return elements
    }

    private fun getSurfacesByTypeNames(map: com.gdx.game.views.world.Map): Map<String, MutableList<GameObject>> {
        val surfacesByType: MutableMap<String, MutableList<GameObject>> = HashMap()

        for (surface in map.getSurfaces()) {
            val typeName = getSurfaceTypeName(surface.textureRef.name)

            if (surfacesByType.containsKey(typeName)) {
                surfacesByType[typeName]!!.add(surface)
            } else {
                val surfacesOfCertainType: MutableList<GameObject> = ArrayList()
                surfacesOfCertainType.add(surface)
                surfacesByType[typeName] = surfacesOfCertainType
            }
        }
        
        return surfacesByType
    }

    private fun getSurfaceTypeName(name: String): String {
        val pathElements: MutableList<String> = ArrayList()

        pathElements.add(ObjectType.SURFACES.objectType)
        pathElements.addAll(name.split(PATHS_SEPARATOR))

        if (pathElements.size > 1) {
            pathElements.removeAt(pathElements.size - 1)
        }

        return java.lang.String.join(PATHS_SEPARATOR, pathElements)
    }

    private fun getObjectsXmlTree(gameObjects: List<GameObject>?, objectTypeName: String, xmlDocument: Document): Element {
        val objects = xmlDocument.createElement(MapFormatStructure.Tags.OBJECTS)
        objects.setAttribute(MapFormatStructure.ObjectsTagAttributes.TYPE, objectTypeName)

        for (gameObject in gameObjects!!) {
            when {
                objectTypeName.contains(ObjectType.CHARACTERS.objectType) -> objects.appendChild(getCharacterXmlNode(gameObject as Character, xmlDocument))
                objectTypeName.contains(ObjectType.ITEMS.objectType) -> objects.appendChild(getItemsXmlNode(gameObject as Item, xmlDocument))
                objectTypeName.contains(ObjectType.OBSTACLES.objectType) -> objects.appendChild(getObstacleXmlNode(gameObject as Obstacle, xmlDocument))
                objectTypeName.contains(ObjectType.SURFACES.objectType) -> objects.appendChild(getSurfaceXmlNode(gameObject as Surface, xmlDocument))
                else -> throw IllegalArgumentException(Errors.NOT_EXISTENT_TYPE)
            }
        }

        return objects
    }

    private fun getCharacterXmlNode(character: Character, xmlDocument: Document): Element {
        val mapObject = xmlDocument.createElement(MapFormatStructure.Tags.OBJECT)

        setObjectDefaultAttributeValues(mapObject, character)
        setObjectSpecificAttributeValues(mapObject, character)

        if (character.isPlayable != CharacterDefaults.PLAYABLE) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.PLAYABLE, character.isPlayable.toString() + "")
        }
        if (character.maxLives != CharacterDefaults.LIVES) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.LIVES, character.maxLives.toString() + "")
        }
        if (character.points != CharacterDefaults.POINTS) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.POINTS, character.points.toString() + "")
        }
        return mapObject
    }

    private fun setObjectDefaultAttributeValues(element: Element, gameObject: GameObject) {
        element.setAttribute(MapFormatStructure.ObjectTagAttributes.NAME, getObjectName(gameObject))

        if (gameObject.textureRef.startFrame != GameObjectDefaults.INDEX) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.INDEX, gameObject.textureRef.startFrame.toString() + "")
        }

        if (gameObject.rotation != GameObjectDefaults.ROTATION) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ROTATION, gameObject.rotation.toString() + "")
        }

        if (gameObject.movement != GameObjectDefaults.MOVEMENT) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.MOVEMENT, gameObject.movement.type)
        }

        element.setAttribute(MapFormatStructure.ObjectTagAttributes.X, gameObject.position.x.toInt().toString())
        element.setAttribute(MapFormatStructure.ObjectTagAttributes.Y, gameObject.position.y.toInt().toString())
    }

    private fun getObjectName(gameObject: GameObject): String {
        val objectPath = gameObject.textureRef.name
        val pathElements = objectPath.split(PATHS_SEPARATOR)
        return pathElements.last()
    }

    private fun setObjectSpecificAttributeValues(element: Element, character: Character) {
        if (character.textureRef.isAnimated != CharacterDefaults.ANIMATED) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ANIMATED, character.textureRef.isAnimated.toString())
        }

        if (character.action.type != CharacterDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_TYPE, character.action.type.actionTypeName)
        }

        if (character.action.type != CharacterDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_VARIABLE, character.action.variable.actionVariable)
        }
    }

    private fun getItemsXmlNode(item: Item, xmlDocument: Document): Element {
        val mapObject = xmlDocument.createElement(MapFormatStructure.Tags.OBJECT)
        setObjectDefaultAttributeValues(mapObject, item)
        setObjectSpecificAttributeValues(mapObject, item)

        if (item.isDraggable != ItemDefaults.DRAGGABLE) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.DRAGGABLE, item.isDraggable.toString())
        }

        return mapObject
    }

    private fun setObjectSpecificAttributeValues(element: Element, item: Item) {
        if (item.textureRef.isAnimated != ItemDefaults.ANIMATED) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ANIMATED, item.textureRef.isAnimated.toString())
        }

        if (item.action.type != ItemDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_TYPE, item.action.type.actionTypeName)
        }

        if (item.action.type != ItemDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_VARIABLE, item.action.variable.actionVariable)
        }
    }

    private fun getObstacleXmlNode(obstacle: Obstacle, xmlDocument: Document): Element {
        val mapObject = xmlDocument.createElement(MapFormatStructure.Tags.OBJECT)

        setObjectDefaultAttributeValues(mapObject, obstacle)
        setObjectSpecificAttributeValues(mapObject, obstacle)

        if (obstacle.isBlocked != ObstacleDefaults.BLOCKED) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.BLOCKED, obstacle.isBlocked.toString())
        }
        if (obstacle.toPosition.x.isNaN() && obstacle.toPosition.y.isNaN()) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.TO_X, obstacle.toPosition.x.toInt().toString())
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.TO_Y, obstacle.toPosition.y.toInt().toString())
        }

        return mapObject
    }

    private fun setObjectSpecificAttributeValues(element: Element, obstacle: Obstacle) {
        if (obstacle.textureRef.isAnimated != ObstacleDefaults.ANIMATED) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ANIMATED, obstacle.textureRef.isAnimated.toString())
        }

        if (obstacle.action.type != ObstacleDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_TYPE, obstacle.action.type.actionTypeName)
        }

        if (obstacle.action.type != ObstacleDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_VARIABLE, obstacle.action.variable.actionVariable)
        }
    }

    private fun getSurfaceXmlNode(surface: Surface, xmlDocument: Document): Element {
        val mapObject = xmlDocument.createElement(MapFormatStructure.Tags.OBJECT)
        setObjectDefaultAttributeValues(mapObject, surface)
        setObjectSpecificAttributeValues(mapObject, surface)

        if (surface.isBlocked != SurfaceDefaults.BLOCKED) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.BLOCKED, surface.isBlocked.toString())
        }

        if (surface.isMirrored != SurfaceDefaults.MIRRORED) {
            mapObject.setAttribute(MapFormatStructure.ObjectTagAttributes.MIRRORED, surface.isMirrored.toString())
        }

        return mapObject
    }

    private fun setObjectSpecificAttributeValues(element: Element, surface: Surface) {
        if (surface.textureRef.isAnimated != SurfaceDefaults.ANIMATED) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ANIMATED, surface.textureRef.isAnimated.toString())
        }

        if (surface.action.type != SurfaceDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_TYPE, surface.action.type.actionTypeName)
        }

        if (surface.action.type != SurfaceDefaults.ACTION.type) {
            element.setAttribute(MapFormatStructure.ObjectTagAttributes.ACTION_VARIABLE, surface.action.variable.actionVariable)
        }
    }
}