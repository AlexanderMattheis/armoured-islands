package com.gdx.game.system.storage.profile

import com.gdx.game.system.defaults.messages.errors.ErrorInfo
import java.util.*

abstract class StepsRunner(private val totalNumberOfSteps: Int) {
    protected var currentNumberOfSteps = 0
    private val exceptionsInfos: MutableList<ErrorInfo>

    val isFinished: Boolean
        get() = currentNumberOfSteps >= totalNumberOfSteps

    val progressPercent: Float
        get() = currentNumberOfSteps.toFloat() / totalNumberOfSteps.toFloat()

    abstract fun executeStep()

    init {
        exceptionsInfos = ArrayList()
    }

    fun addExceptionsInfo(exception: Exception, message: String) {
        exceptionsInfos.add(ErrorInfo(exception, message))
    }
}