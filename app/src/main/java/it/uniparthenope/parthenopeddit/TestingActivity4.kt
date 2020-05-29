package it.uniparthenope.parthenopeddit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import it.uniparthenope.parthenopeddit.api.requests.CommentsRequests
import kotlinx.android.synthetic.main.activity_testing4.*

class TestingActivity4 : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing4)

        button_test_1.setOnClickListener {
            CommentsRequests(this, app.auth).publishNewComment(
                input_test_1.text.toString(),
                1,
                {
                    output_test_1.text = it.id.toString()
                }, {
                    output_test_1.text = it
                }
            )
        }

        button_test_2.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                CommentsRequests(this, app.auth).getComment(
                    id,
                    {
                        output_test_2.text = it.body
                    }, {
                        output_test_2.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need comment id", Toast.LENGTH_SHORT).show()
            }
        }

        button_test_3.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                CommentsRequests(this, app.auth).getCommentWithComments(
                    id,
                    {
                        output_test_3.text = it.comments_num.toString()
                    }, {
                        output_test_3.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need comment id", Toast.LENGTH_SHORT).show()
            }
        }

        button_test_4.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                CommentsRequests(this, app.auth).likeComment(
                    id,
                    {
                        output_test_4.text = "LIKED COMMENT"
                    }, {
                        output_test_4.text = "REMOVED LIKE FROM COMMENT"
                    }, {
                        output_test_4.text = "REMOVED DISLIKE AND LIKED COMMENT"
                    }, {
                        output_test_4.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need comment id", Toast.LENGTH_SHORT).show()
            }
        }

        button_test_5.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                CommentsRequests(this, app.auth).dislikeComment(
                    id,
                    {
                        output_test_5.text = "DISLIKED COMMENT"
                    }, {
                        output_test_5.text = "REMOVED DISLIKE FROM COMMENT"
                    }, {
                        output_test_5.text = "REMOVED LIKE AND DISLIKED COMMENT"
                    }, {
                        output_test_5.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need comment id", Toast.LENGTH_SHORT).show()
            }
        }

        next_test.setOnClickListener {
            goToActivity(TestingActivity5::class.java)
        }
    }
}
