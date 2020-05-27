package it.uniparthenope.parthenopeddit

import android.app.Application
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.RuntimeException
import java.net.URISyntaxException

class App : Application() {
    var currentActivity: BasicActivity? = null

    private var mSocket: Socket? = null
    private val URL = "http://127.0.0.1:5000"

    override fun onCreate() {
        super.onCreate()
        mSocket = try {
            IO.socket(URL)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    fun getmSocket(): Socket? {
        return mSocket
    }
}