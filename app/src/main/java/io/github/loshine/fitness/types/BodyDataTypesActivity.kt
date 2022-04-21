package io.github.loshine.fitness.types

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import io.github.loshine.fitness.FitnessDataType
import io.github.loshine.fitness.buildFitnessOptions

class BodyDataTypesActivity : CommonDataTypesActivity() {
    override val data: List<String> = listOf(
        "Body fat percentage",
        "Heart rate",
        "Height",
        "Weight",
    )
    override val fitnessOptions: FitnessOptions = FitnessDataType.Body.buildFitnessOptions()

    override fun getDataTypeByName(name: String): DataType {
        return when (name) {
            "Body fat percentage" -> DataType.TYPE_BODY_FAT_PERCENTAGE
            "Heart rate" -> DataType.TYPE_HEART_RATE_BPM
            "Height" -> DataType.TYPE_HEIGHT
            "Weight" -> DataType.TYPE_WEIGHT
            else -> DataType.TYPE_BODY_FAT_PERCENTAGE
        }
    }
}