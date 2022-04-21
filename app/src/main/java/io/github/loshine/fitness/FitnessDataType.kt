package io.github.loshine.fitness

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

enum class FitnessDataType {
    Activity, Body, Location, Nutrition, Sleep, Health
}

fun FitnessDataType.buildFitnessOptions(): FitnessOptions {
    val types = when (this) {
        FitnessDataType.Activity -> listOf(
            DataType.TYPE_ACTIVITY_SEGMENT,
            DataType.TYPE_BASAL_METABOLIC_RATE,
            DataType.TYPE_CALORIES_EXPENDED,
            DataType.TYPE_CYCLING_PEDALING_CADENCE,
            DataType.TYPE_CYCLING_PEDALING_CUMULATIVE,
            DataType.TYPE_HEART_POINTS,
            DataType.TYPE_MOVE_MINUTES,
            DataType.TYPE_POWER_SAMPLE,
            DataType.TYPE_STEP_COUNT_CADENCE,
            DataType.TYPE_STEP_COUNT_DELTA,
            DataType.TYPE_WORKOUT_EXERCISE,
        )
        FitnessDataType.Body -> listOf(
            DataType.TYPE_BODY_FAT_PERCENTAGE,
            DataType.TYPE_HEART_RATE_BPM,
            DataType.TYPE_HEIGHT,
            DataType.TYPE_WEIGHT,
        )
        else -> listOf()
    }
    return FitnessOptions.builder()
        .apply { types.forEach { addDataType(it) } }
        .build()
}