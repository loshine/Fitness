package io.github.loshine.fitness

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.material.divider.MaterialDividerItemDecoration
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
        readData(getDataTypeByName(text))
    }

    private fun readData(dataType: DataType) {
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
            .addOnSuccessListener { response -> printData(response) }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the step count.", e)
            }
    }

    private fun printData(dataReadResult: DataReadResponse) {
        // [START parse_read_data_result]
        // If the DataReadRequest object specified aggregated data, dataReadResult will be returned
        // as buckets containing DataSets, instead of just DataSets.
        if (dataReadResult.buckets.isNotEmpty()) {
            Log.i(TAG, "Number of returned buckets of DataSets is: " + dataReadResult.buckets.size)
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach { dumpDataSet(it) }
            }
        } else if (dataReadResult.dataSets.isNotEmpty()) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.dataSets.size)
            dataReadResult.dataSets.forEach { dumpDataSet(it) }
        }
        // [END parse_read_data_result]
    }
}