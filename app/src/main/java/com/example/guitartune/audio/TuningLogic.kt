package com.example.guitartune.audio

import kotlin.math.abs

object TuningLogic {
    // 标准吉他调弦频率（双精度）
    private val STANDARD_TUNING = mapOf(
        "E" to 82.41,
        "A" to 110.00,
        "D" to 146.83,
        "G" to 196.00,
        "B" to 246.94,
        "e" to 329.63
    )

    fun findClosestNote(frequency: Double): Pair<String, Double> {
        val (note, closest) = STANDARD_TUNING.minBy { abs(it.value - frequency) }
        return note to (frequency - closest)
    }
}