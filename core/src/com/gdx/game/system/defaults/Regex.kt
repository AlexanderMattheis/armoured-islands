package com.gdx.game.system.defaults

import kotlin.text.Regex as KotlinRegex

object Regex {
    val INTEGER = KotlinRegex("-?\\d+")
    val POSITIVE_INTEGER = KotlinRegex("^[1-9]\\d*$")
    val POSITIVE_INTEGER_OR_ZERO = KotlinRegex("\\d+")
    val NUMBER = KotlinRegex("[0-9]")
    val ZEROS = KotlinRegex("0*")
}