package it.uniparthenope.parthenopeddit

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth
import kotlinx.android.synthetic.main.activity_testing3.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class TestingActivity3 : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing3)

        val auth = SharedPreferencesAuth.getInstance(this)

        fetch_button.setOnClickListener {
            UserRequests(this, auth).getUserFeed(1, 5, {
                output_textview.text = "fetched ${it.size} posts"
            }, {
                output_textview.text = "ERROR $it"
            })
        }
    }
}
