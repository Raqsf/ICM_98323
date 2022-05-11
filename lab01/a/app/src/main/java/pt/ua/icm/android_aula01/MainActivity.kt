package pt.ua.icm.android_aula01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "pt.ua.icm.android_aula01.MESSAGE"
const val TEXT_REQUEST = 1

class MainActivity : AppCompatActivity() {
    private var mReplyHeadTextView: TextView? = null
    private var mReplyTextView: TextView? = null
    private val LOG_TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mReplyHeadTextView = findViewById(R.id.textView2)
        mReplyTextView = findViewById(R.id.textView3)
        // Restore the state.
        if (savedInstanceState != null) {
            val isVisible = savedInstanceState.getBoolean("reply_visible")
            if (isVisible) {
                mReplyHeadTextView?.visibility = View.VISIBLE
                mReplyTextView?.text = savedInstanceState.getString("reply_text")
            }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mReplyHeadTextView?.visibility == View.VISIBLE) {
            outState.putBoolean("reply_visible", true)
            outState.putString("reply_text",mReplyTextView?.text.toString())
        }
    }

    /** Called when the user taps the Send button */
    @Suppress("DEPRECATION")
    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivityForResult(intent, TEXT_REQUEST)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                val reply = data?.getStringExtra(EXTRA_REPLY)
                mReplyHeadTextView!!.visibility = View.VISIBLE
                mReplyTextView!!.text = reply
                mReplyTextView!!.visibility = View.VISIBLE
            }
        }
    }
}