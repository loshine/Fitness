package io.github.loshine.fitness

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = SimpleTextAdapter(
            FitnessDataType.values().map { it.name }.toList(),
            this::onItemClick
        )
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            MaterialDividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun onItemClick(v: View, pos: Int, text: String) {
        when (text) {
            FitnessDataType.Activity.name -> fitSignIn(FitnessDataType.Activity)
            FitnessDataType.Body.name -> fitSignIn(FitnessDataType.Body)
        }
    }

    /**
     * Checks that the user is signed in, and if so, executes the specified function. If the user is
     * not signed in, initiates the sign in flow, specifying the post-sign in function to execute.
     *
     * @param requestCode The request code corresponding to the action to perform after sign in.
     */
    private fun fitSignIn(fitnessDataType: FitnessDataType) {
        val options = fitnessDataType.buildFitnessOptions()
        if (oAuthPermissionsApproved(options)) {
            performAction(fitnessDataType.ordinal)
        } else {
            GoogleSignIn.requestPermissions(
                this,
                fitnessDataType.ordinal,
                getGoogleAccount(options),
                options
            )
        }
    }

    private fun performAction(requestCode: Int) {
        val destination = when (requestCode) {
            FitnessDataType.Activity.ordinal -> ActivityDataTypesActivity::class.java
            FitnessDataType.Body.ordinal -> BodyDataTypesActivity::class.java
            else -> ActivityDataTypesActivity::class.java
        }
        startActivity(destination)
    }

    /**
     * Handles the callback from the OAuth sign in flow, executing the post sign in function
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> performAction(requestCode)
            else -> oAuthErrorMsg(requestCode, resultCode)
        }
    }

    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
        val message = """
            There was an error signing into Fit. Check the troubleshooting section of the README
            for potential issues.
            Request code was: $requestCode
            Result code was: $resultCode
        """.trimIndent()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun oAuthPermissionsApproved(fitnessOptions: FitnessOptions) =
        GoogleSignIn.hasPermissions(getGoogleAccount(fitnessOptions), fitnessOptions)

    private fun getGoogleAccount(fitnessOptions: FitnessOptions) =
        GoogleSignIn.getAccountForExtension(this, fitnessOptions)
}