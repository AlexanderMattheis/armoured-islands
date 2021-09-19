package com.gdx.game.views.screens.compositing.adder

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.stages.MenuFloating

object StageAdder: Adder() {

    fun addTo(stage: Stage, actor: Actor) {
        stage.addActor(actor)
    }

    fun addTo(stage: Stage, actor: Actor, position: Vector2, dimension: Vector2, menuFloating: MenuFloating) {
        Proportioner.setDimension(actor, dimension)
        Proportioner.setPosition(actor, position, menuFloating)
        addTo(stage, actor)
    }

    fun addTo(actor: Actor, stage: Stage, position: Vector2, menuFloating: MenuFloating) {
        Proportioner.setPosition(actor, position, menuFloating)
        addTo(stage, actor)
    }

    fun addTo(actor: Actor, stage: Stage, position: Vector2, alignment: Int, menuFloating: MenuFloating) {
        Proportioner.setPosition(actor, position, alignment, menuFloating)
        addTo(stage, actor)
    }

    fun addTo(actor: Actor, stage: Stage, position: Vector2, dimensions: Vector2, alignment: Int, menuFloating: MenuFloating) {
        Proportioner.setDimension(actor, dimensions)
        Proportioner.setPosition(actor, position, alignment, menuFloating)
        addTo(stage, actor)
    }

    fun addTo(stage: Stage, actors: Array<Actor>) {
        for (actor in actors) {
            stage.addActor(actor)
        }
    }
}