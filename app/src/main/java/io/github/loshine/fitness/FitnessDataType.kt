package io.github.loshine.fitness

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.HealthDataTypes

enum class FitnessDataType {
    Activity, Body, Location, Nutrition, Health
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
        FitnessDataType.Location -> listOf(
            DataType.TYPE_CYCLING_WHEEL_RPM,
            DataType.TYPE_CYCLING_WHEEL_REVOLUTION,
            DataType.TYPE_DISTANCE_DELTA,
            DataType.TYPE_LOCATION_SAMPLE,
            DataType.TYPE_SPEED,
        )
        FitnessDataType.Nutrition -> listOf(
            DataType.TYPE_HYDRATION,
            DataType.TYPE_NUTRITION,
        )
//        FitnessDataType.Sleep -> listOf(
//            DataType.TYPE_SLEEP_SEGMENT,
//        )
        FitnessDataType.Health -> listOf(
            HealthDataTypes.TYPE_BLOOD_GLUCOSE,
            HealthDataTypes.TYPE_BLOOD_PRESSURE,
            HealthDataTypes.TYPE_BODY_TEMPERATURE,
            HealthDataTypes.TYPE_CERVICAL_MUCUS,
            HealthDataTypes.TYPE_CERVICAL_POSITION,
            HealthDataTypes.TYPE_MENSTRUATION,
            HealthDataTypes.TYPE_OVULATION_TEST,
            HealthDataTypes.TYPE_OXYGEN_SATURATION,
            HealthDataTypes.TYPE_VAGINAL_SPOTTING,
        )
        else -> listOf()
    }
    return FitnessOptions.builder()
        .apply { types.forEach { addDataType(it) } }
        .build()
}