package edu.kamshanski.daggerstudy.util

import kotlin.random.Random

object Rand : Random() {

    private val random = Random(85)

    override fun nextBits(bitCount: Int): Int =
        random.nextBits(bitCount)
}

inline fun <reified T : Enum<T>> anyEnum(): T = T::class.java
    .enumConstants
    .random()