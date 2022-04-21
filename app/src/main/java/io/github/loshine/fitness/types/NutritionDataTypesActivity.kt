package io.github.loshine.fitness.types

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import io.github.loshine.fitness.FitnessDataType
import io.github.loshine.fitness.buildFitnessOptions

class NutritionDataTypesActivity : CommonDataTypesActivity() {
    override val data: List<String> = listOf(
        "Hydration",
        "Nutrition"
    )
    override val fitnessOptions: FitnessOptions = FitnessDataType.Nutrition.buildFitnessOptions()

    override fun getDataTypeByName(name: String): DataType {
        return when (name) {
            "Hydration" -> DataType.TYPE_HYDRATION
            "Nutrition" -> DataType.TYPE_NUTRITION
            else -> DataType.TYPE_NUTRITION
        }
    }
}