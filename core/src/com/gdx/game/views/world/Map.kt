package com.gdx.game.views.world

import com.badlogic.gdx.math.Vector2
import com.gdx.game.views.world.objects.GameObject
import com.gdx.game.views.world.objects.types.Character
import com.gdx.game.views.world.objects.types.Item
import com.gdx.game.views.world.objects.types.Obstacle
import com.gdx.game.views.world.objects.types.Surface
import java.io.Serializable
import java.util.*

class Map(var name: String, private var dimensions: Vector2) : Serializable {
    private var characters: MutableList<GameObject>
    private var items: MutableList<GameObject>
    private var obstacles: MutableList<GameObject>
    private var surfaces: MutableList<GameObject>

    private var charactersMap: Array<Array<GameObject?>>
    private var itemsMap: Array<Array<GameObject?>>
    private var obstaclesMap: Array<Array<GameObject?>>
    private var surfacesMap: Array<Array<GameObject?>>

    init {
        characters = ArrayList()
        items = ArrayList()
        obstacles = ArrayList()
        surfaces = ArrayList()

        charactersMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
        itemsMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
        obstaclesMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
        surfacesMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
    }

    fun getDimensions(): Vector2 {  // TODO: better solution
        return dimensions
    }

    fun setDimensions(dimensions: Vector2) {
        this.dimensions = dimensions

        charactersMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
        itemsMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
        obstaclesMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
        surfacesMap = Array(dimensions.x.toInt()) { arrayOfNulls<GameObject>(dimensions.y.toInt()) }
    }

    fun clear() {
        characters.clear()
        items.clear()
        obstacles.clear()
        surfaces.clear()
    }

    fun getCharacters(): MutableList<GameObject> {
        return characters
    }

    private fun getCharacter(position: Vector2): GameObject? {
        return charactersMap[position.x.toInt()][position.y.toInt()]
    }

    fun setCharacters(characters: MutableList<GameObject>) {
        this.characters = characters
        addToMap(charactersMap, characters)
    }

    private fun addToMap(map: Array<Array<GameObject?>>, elements: List<GameObject>) {
        for (gameObject in elements) {
            val position = gameObject.position
            map[position.x.toInt()][position.y.toInt()] = gameObject
        }
    }

    fun addCharacter(character: GameObject) {
        addObject(character, characters, charactersMap)
    }

    private fun removeCharacter(character: GameObject?) {
        remove(character, characters, charactersMap)
    }

    private fun addObject(gameObject: GameObject, objectsList: MutableList<GameObject>, objectsMap: Array<Array<GameObject?>>) {
        val position = gameObject.position
        objectsList.add(gameObject)
        objectsMap[position.x.toInt()][position.y.toInt()] = gameObject
    }

    private fun remove(gameObject: GameObject?, objectsList: MutableList<GameObject>, objectsMap: Array<Array<GameObject?>>) {
        if (gameObject != null) {
            val position = gameObject.position
            objectsList.remove(gameObject)
            objectsMap[position.x.toInt()][position.y.toInt()] = null
        }
    }

    fun add(gameObject: GameObject) {
        when (gameObject) {
            is Character -> addCharacter(gameObject)
            is Item -> addItem(gameObject)
            is Obstacle -> addObstacle(gameObject)
            is Surface -> addSurface(gameObject)
        }
    }

    fun remove(gameObject: GameObject) {
        when (gameObject) {
            is Character -> removeCharacter(gameObject)
            is Item -> removeItem(gameObject)
            is Obstacle -> removeObstacle(gameObject)
            is Surface -> removeSurface(gameObject)
        }
    }

    fun getItems(): MutableList<GameObject> {
        return items
    }

    private fun getItem(position: Vector2): GameObject? {
        return itemsMap[position.x.toInt()][position.y.toInt()]
    }

    fun setItems(items: MutableList<GameObject>) {
        this.items = items
        addToMap(itemsMap, items)
    }

    fun addItem(item: GameObject) {
        addObject(item, items, itemsMap)
    }

    private fun removeItem(item: GameObject?) {
        remove(item, items, itemsMap)
    }

    fun getObstacles(): MutableList<GameObject> {
        return obstacles
    }

    private fun getObstacle(position: Vector2): GameObject? {
        return obstaclesMap[position.x.toInt()][position.y.toInt()]
    }

    fun setObstacles(obstacles: MutableList<GameObject>) {
        this.obstacles = obstacles
        addToMap(obstaclesMap, obstacles)
    }

    fun addObstacle(obstacle: GameObject) {
        addObject(obstacle, obstacles, obstaclesMap)
    }

    private fun removeObstacle(obstacle: GameObject?) {
        remove(obstacle, obstacles, obstaclesMap)
    }

    fun getSurfaces(): MutableList<GameObject> {
        return surfaces
    }

    fun getSurface(position: Vector2): GameObject {
        return surfacesMap[position.x.toInt()][position.y.toInt()]!!   // there should always a surface below
    }

    fun setSurfaces(surfaces: MutableList<GameObject>) {
        this.surfaces = surfaces
        addToMap(surfacesMap, surfaces)
    }

    fun addSurface(surface: GameObject) {
        addObject(surface, surfaces, surfacesMap)
    }

    private fun removeSurface(surface: GameObject?) {
        remove(surface, surfaces, surfacesMap)
    }

    fun getObjects(position: Vector2): List<GameObject> {
        return listOfNotNull(getCharacter(position), getItem(position), getObstacle(position), getSurface(position))
    }

    fun popObjects(position: Vector2): List<GameObject> {
        val character = getCharacter(position)
        val item = getItem(position)
        val obstacle = getObstacle(position)
        val surface = getSurface(position)

        removeCharacter(character)
        removeItem(item)
        removeObstacle(obstacle)
        removeSurface(surface)

        return listOfNotNull(character, item, obstacle, surface)
    }
}