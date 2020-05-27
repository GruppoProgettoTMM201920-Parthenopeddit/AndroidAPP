package it.uniparthenope.parthenopeddit

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_testing3.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

/*
private var mSocket: Socket? = null
    get() {
        return try {
            field = IO.socket("http://127.0.0.1:5000")
            field
        } catch (e: URISyntaxException) {
            null
        }
    }

 */


class TestingActivity3 : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing3)

        app.getmSocket()?.connect()

        if (app.getmSocket()?.connected() == true) {
            Toast.makeText(this, "Connected!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not connected!!", Toast.LENGTH_SHORT).show();
        }
    }
}
