package io.github.loshine.fitness

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class ActivityDataTypesActivity : CommonDataTypesActivity() {
    override val data: List<String> = listOf(
        "Activity",
        "Basal metabolic rate (BMR)",
        "Calories burned",
//                "Cycling pedaling cadence",
//                "Cycling pedaling cumulative",
        "Heart Points",
        "Move Minutes",
        "Power",
//                "Step count cadence",
        "Step count delta",
//                "Workout"
    )
    override val fitnessOptions: FitnessOptions = FitnessDataType.Activity.buildFitnessOptions()

    override fun getDataTypeByName(name: String): DataType {
        return when (name) {
            "Activity" -> DataType.TYPE_ACTIVITY_SEGMENT
            "Basal metabolic rate (BMR)" -> DataType.TYPE_BASAL_METABOLIC_RATE
            "Calories burned" -> DataType.TYPE_CALORIES_EXPENDED
//            "Cycling pedaling cadence" -> DataType.TYPE_CYCLING_PEDALING_CADENCE
//            "Cycling pedaling cumulative" -> DataType.TYPE_CYCLING_PEDALING_CUMULATIVE
            "Heart Points" -> DataType.TYPE_HEART_POINTS
            "Move Minutes" -> DataType.TYPE_MOVE_MINUTES
            "Power" -> DataType.TYPE_POWER_SAMPLE
            "Step count cadence" -> DataType.TYPE_STEP_COUNT_CADENCE
            "Step count delta" -> DataType.TYPE_STEP_COUNT_DELTA
//            "Workout" -> DataType.TYPE_WORKOUT_EXERCISE
            else -> DataType.TYPE_STEP_COUNT_DELTA
        }
    }
}