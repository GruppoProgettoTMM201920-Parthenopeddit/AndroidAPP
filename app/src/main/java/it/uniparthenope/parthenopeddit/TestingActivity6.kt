package it.uniparthenope.parthenopeddit

import android.os.Bundle
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import kotlinx.android.synthetic.main.activity_testing6.*

class TestingActivity6 : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing6)

        test_button_1.setOnClickListener {
            GroupsRequests(this, app.auth).getUserGroups(
                {
                    if(it.size == 1) {
                        output_test_1.text = "${it.size}, name : ${it[0].group!!.name}"
                    } else {
                        output_test_1.text = it.size.toString()
                    }
                }, {
                    output_test_1.text = it
                }
            )
        }

        test_button_2.setOnClickListener {
            GroupsRequests(this, app.auth).createGroup(
                "TEST",
                listOf("user2", "user3"),
                {
                    output_test_2.text = it.size.toString()
                }, {
                    output_test_2.text = it
                }
            )
        }
        /** works **/
        test_button_2.isClickable = false
    }
}
