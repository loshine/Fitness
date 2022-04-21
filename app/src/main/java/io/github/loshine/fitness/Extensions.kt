package io.github.loshine.fitness

/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
//import io.github.loshine.fitness.log.Log
import java.text.DateFormat
import java.util.concurrent.TimeUnit

const val TAG = "BasicHistoryApi"

// [START parse_dataset]
fun dumpDataSet(dataSet: DataSet) {
    Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")

    for (dp in dataSet.dataPoints) {
        Log.i(TAG, "Data point:")
        Log.i(TAG, "\tType: ${dp.dataType.name}")
        Log.i(TAG, "\tStart: ${dp.getStartTimeString()}")
        Log.i(TAG, "\tEnd: ${dp.getEndTimeString()}")
        dp.dataType.fields.forEach {
            Log.i(TAG, "\tField: ${it.name} Value: ${dp.getValue(it)}")
        }
    }
}

fun DataPoint.getStartTimeString(): String = DateFormat.getDateTimeInstance()
    .format(this.getStartTime(TimeUnit.MILLISECONDS))

fun DataPoint.getEndTimeString(): String = DateFormat.getDateTimeInstance()
    .format(this.getEndTime(TimeUnit.MILLISECONDS))