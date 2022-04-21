package io.github.loshine.fitness

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This sample demonstrates how to use the History API of the Google Fit platform to insert data,
 * query against existing data, and remove data. It also demonstrates how to authenticate a user
 * with Google Play Services and how to properly represent data in a {@link DataSet}.
 */
class LogActivity : AppCompatActivity(R.layout.activity_log) {

    companion object {

        private const val KEY_TYPE = "type"
        private const val KEY_DATA = "data"
        fun start(context: Context, type: String, data: String) {
            context.startActivity(Intent(context, LogActivity::class.java).apply {
                putExtra(KEY_TYPE, type)
                putExtra(KEY_DATA, data)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra(KEY_TYPE)
        findViewById<TextView>(R.id.title_text_view).text = type

        val data = intent.getStringExtra(KEY_DATA)
        findViewById<TextView>(R.id.log_view).text = data
    }
}