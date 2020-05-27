package it.uniparthenope.parthenopeddit

import android.os.Bundle
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth
import kotlinx.android.synthetic.main.activity_testing2.*

class TestingActivity2 : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing2)

        val auth = SharedPreferencesAuth(this)

        publish.setOnClickListener {
            var board_id:Int = 0
            if( post_board_id.text.toString() != "" ) {
                board_id = post_board_id.text.toString().toInt()
            }
            PostsRequests(this, auth).publishNewPost(
                post_title.text.toString(),
                post_body.text.toString(),
                board_id,

                {
                    output.text = "success = ${it.title}, ${it.body}"
                }, {
                    output.text = "error $it"
                }
            )
        }

        next_test.setOnClickListener {
            goToActivity(TestingActivity3::class.java)
        }
    }
}
