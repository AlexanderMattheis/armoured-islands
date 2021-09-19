package com.gdx.game.system.settings

import com.badlogic.gdx.math.Vector2
import com.gdx.game.system.defaults.Regex
import java.util.*

class ValueParser {
    fun getBoolean(settings: Properties, property: String, defaultValue: Boolean): Boolean {
        val value: String = settings.getProperty(property)
        return if (value == "true" || value == "false") value.toBoolean() else defaultValue
    }

    fun getInteger(settings: Properties, property: String, defaultValue: Int): Int {
        val value = settings.getProperty(property)
        return if (value.matches(Regex.INTEGER)) value.toInt() else defaultValue
    }

    fun getDimensions(settings: Properties, property1: String,
                      property2: String, defaultValue: Vector2): Vector2 {
        val valueX = settings.getProperty(property1)
        val valueY = settings.getProperty(property2)

        return if (areNaturalNumbersOrZeros(valueX, valueY)) Vector2(toRoundedNum(valueX), toRoundedNum(valueY)) else defaultValue
    }

    private fun areNaturalNumbersOrZeros(numberA: String, numberB: String): Boolean {
        val positiveOrZero = Regex.POSITIVE_INTEGER_OR_ZERO
        return numberA.matches(positiveOrZero) && numberB.matches(positiveOrZero)
    }

    private fun toRoundedNum(number: String): Float {
        return number.toInt().toFloat()
    }
}