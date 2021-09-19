package com.gdx.game.views.screens.stages.huds

import com.badlogic.gdx.scenes.scene2d.Stage
import com.gdx.game.views.screens.stages.dialogs.Dialog
import com.gdx.game.views.world.objects.GameObject

abstract class Hud(): Stage() {
    abstract val dialogs: Array<Dialog>
    abstract val selectedObject: GameObject?
}