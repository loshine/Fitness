package io.github.loshine.fitness.types

import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.HealthDataTypes
import io.github.loshine.fitness.FitnessDataType
import io.github.loshine.fitness.buildFitnessOptions

class HealthDataTypesActivity : CommonDataTypesActivity() {
    override val data: List<String> = listOf(
        "Blood glucose",
        "Blood pressure",
        "Body temperature",
        "Cervical mucus",
        "Cervical position",
        "Menstruation",
        "Ovulation test",
        "Oxygen saturation",
        "Vaginal spotting",
    )
    override val fitnessOptions: FitnessOptions = FitnessDataType.Health.buildFitnessOptions()

    override fun getDataTypeByName(name: String): DataType {
        return when (name) {
            "Blood glucose" -> HealthDataTypes.TYPE_BLOOD_GLUCOSE
            "Blood pressure" -> HealthDataTypes.TYPE_BLOOD_PRESSURE
            "Body temperature" -> HealthDataTypes.TYPE_BODY_TEMPERATURE
            "Cervical mucus" -> HealthDataTypes.TYPE_CERVICAL_MUCUS
            "Cervical position" -> HealthDataTypes.TYPE_CERVICAL_POSITION
            "Menstruation" -> HealthDataTypes.TYPE_MENSTRUATION
            "Ovulation test" -> HealthDataTypes.TYPE_OVULATION_TEST
            "Oxygen saturation" -> HealthDataTypes.TYPE_OXYGEN_SATURATION
            "Vaginal spotting" -> HealthDataTypes.TYPE_VAGINAL_SPOTTING
            else -> HealthDataTypes.TYPE_BLOOD_GLUCOSE
        }
    }
}