package io.github.loshine.fitness.types

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import io.github.loshine.fitness.FitnessDataType
import io.github.loshine.fitness.buildFitnessOptions

class LocationDataTypesActivity : CommonDataTypesActivity() {
    override val data: List<String> = listOf(
//        "Cycling wheel revolution RPM",
//        "Cycling wheel revolution cumulative",
        "Distance delta",
        "Location sample",
        "Speed",
    )
    override val fitnessOptions: FitnessOptions = FitnessDataType.Location.buildFitnessOptions()

    override fun getDataTypeByName(name: String): DataType {
        return when (name) {
            "Cycling wheel revolution RPM" -> DataType.TYPE_CYCLING_WHEEL_RPM
            "Cycling wheel revolution cumulative" -> DataType.TYPE_CYCLING_WHEEL_REVOLUTION
            "Distance delta" -> DataType.TYPE_DISTANCE_DELTA
            "Location sample" -> DataType.TYPE_LOCATION_SAMPLE
            "Speed" -> DataType.TYPE_SPEED
            else -> DataType.TYPE_SPEED
        }
    }
}