package it.uniparthenope.parthenopeddit

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
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

        button_test_1.setOnClickListener {
            PostsRequests(this, auth).getPost(
                1,
                {
                    output_test_1.text = "Successo: ${it.title}"
                },
                {
                    output_test_1.text = it
                }
            )
        }

        button_test_2.setOnClickListener {
            PostsRequests(this, auth).getPostWithComments(
                1,
                {
                    output_test_2.text = "Successo: ${it.title}, ${it.comments!!.size} top level comments"
                }, {
                    output_test_2.text = it
                }
            )
        }

        button_test_3.setOnClickListener {
            PostsRequests(this, auth).likePost(
                1,
                {
                    output_test_3.text = "LIKED POST"
                }, {
                    output_test_3.text = "REMOVED LIKE FROM POST"
                }, {
                    output_test_3.text = "REMOVED DISLIKE AND LIKED POST"
                }, {
                    output_test_3.text = it
                }
            )
        }

        button_test_4.setOnClickListener {
            PostsRequests(this, auth).dislikePost(
                1,
                {
                    output_test_4.text = "DISLIKED POST"
                }, {
                    output_test_4.text = "REMOVED DISLIKE FROM POST"
                }, {
                    output_test_4.text = "REMOVED LIKE AND DISLIKED POST"
                }, {
                    output_test_4.text = it
                }
            )
        }

        next_test.setOnClickListener {
            goToActivity(TestingActivity4::class.java)
        }
    }
}
