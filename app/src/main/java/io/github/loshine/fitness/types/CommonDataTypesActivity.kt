package io.github.loshine.fitness.types

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.github.loshine.fitness.LogActivity
import io.github.loshine.fitness.R
import io.github.loshine.fitness.SimpleTextAdapter
import io.github.loshine.fitness.TAG
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

abstract class CommonDataTypesActivity : AppCompatActivity(R.layout.activity_common_data_types) {

    abstract val data: List<String>
    abstract val fitnessOptions: FitnessOptions

    abstract fun getDataTypeByName(name: String): DataType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = SimpleTextAdapter(data) { _, _, text -> readDataByName(text) }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            MaterialDividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun readDataByName(text: String) {
        readData(text, getDataTypeByName(text))
    }

    private fun readData(text: String, dataType: DataType) {
        // Read the data that's been collected throughout the past week.
        val endTime = LocalDate.now().atTime(LocalTime.MIN).atZone(ZoneId.systemDefault())
        val startTime = endTime.minusWeeks(1)
        Log.i(TAG, "Range Start: $startTime")
        Log.i(TAG, "Range End: $endTime")

        val readRequest = DataReadRequest.Builder()
            // The data request can specify multiple data types to return, effectively
            // combining multiple data queries into one call.
            // In this example, it's very unlikely that the request is for several hundred
            // datapoints each consisting of a few steps and a timestamp.  The more likely
            // scenario is wanting to see how many steps were walked per day, for 7 days.
            .aggregate(dataType)
            // Analogous to a "Group By" in SQL, defines how data should be aggregated.
            // bucketByTime allows for a time span, whereas bucketBySession would allow
            // bucketing by "sessions", which would need to be defined in code.
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
            .build()

        Fitness.getHistoryClient(
            this,
            GoogleSignIn.getAccountForExtension(
                this,
                fitnessOptions
            )
        ).readData(readRequest)
//            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { response -> printData(text, response) }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the step count.", e)
            }
    }

    private fun printData(text: String, dataReadResult: DataReadResponse) {
        val builder = StringBuilder()
        if (dataReadResult.buckets.isNotEmpty()) {
            Log.i(TAG, "Number of returned buckets of DataSets is: " + dataReadResult.buckets.size)
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach { dumpDataSet(builder, it) }
            }
        } else if (dataReadResult.dataSets.isNotEmpty()) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.dataSets.size)
            dataReadResult.dataSets.forEach { dumpDataSet(builder, it) }
        }
        LogActivity.start(this, text, builder.toString())
    }


    // [START parse_dataset]
    private fun dumpDataSet(builder: StringBuilder, dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")

        for (dp in dataSet.dataPoints) {
//            Log.i(TAG, "Data point:")
//            Log.i(TAG, "\tType: ${dp.dataType.name}")
//            Log.i(TAG, "\tStart: ${dp.getStartTimeString()}")
//            Log.i(TAG, "\tEnd: ${dp.getEndTimeString()}")
//            builder.append("\n\nStart: ${dp.getStartTimeString()} to End: ${dp.getEndTimeString()}")
            builder.append("\n\nDate: ${dp.getStartTimeString()}")
            dp.dataType.fields.forEach {
//                Log.i(TAG, "\tField: ${it.name} Value: ${dp.getValue(it)}")
                builder.append("\n${it.name}: ${dp.getValue(it)}")
            }
        }
    }

    private fun DataPoint.getStartTimeString(): String = DateFormat.getDateInstance()
        .format(this.getStartTime(TimeUnit.MILLISECONDS))

    private fun DataPoint.getEndTimeString(): String = DateFormat.getDateTimeInstance()
        .format(this.getEndTime(TimeUnit.MILLISECONDS))
}