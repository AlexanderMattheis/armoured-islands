package com.gdx.game.system.storage.mapping.loader

import com.gdx.game.views.world.objects.GameObject

class GameObjectData(val characters: MutableList<GameObject>, val items: MutableList<GameObject>,
                     val obstacles: MutableList<GameObject>, val surfaces: MutableList<GameObject>) : Data