package pt.ua.icm.android_aula01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_REPLY = "pt.ua.icm.android_aula01.REPLY"

class DisplayMessageActivity : AppCompatActivity() {

    private val LOG_TAG = "DisplayMessageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    /** Called when the user taps the Send button */
    fun sendReply(view: View) {
        val editText = findViewById<EditText>(R.id.editTextTextPersonName2)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_REPLY, message)
        }
        setResult(RESULT_OK, intent)
        Log.d(LOG_TAG, "End SecondActivity")
        finish()
    }
}