package com.gdx.game.views.world.editor.management.tracking

class MapChangeList {
    private val undoList: MutableList<MapChange> = mutableListOf()
    private val redoList: MutableList<MapChange> = mutableListOf()

    val undoChange: MapChange
        get() = getChange(undoList, redoList)


    val redoChange: MapChange
        get() = getChange(redoList, undoList)

    fun hasChanges(): Boolean {
        return undoList.isNotEmpty()
    }

    fun hasRepetitions(): Boolean {
        return redoList.isNotEmpty()
    }

    fun emptyRedoList() {
        redoList.clear()
    }

    fun captureChange(mapChange: MapChange) {
        undoList.add(mapChange)
    }

    private fun getChange(popList: MutableList<MapChange>, pushList: MutableList<MapChange>): MapChange {
        val change = popList.removeAt(popList.count() - 1)  // removes from the end
        pushList.add(change)  // adds to the end
        return change
    }
}