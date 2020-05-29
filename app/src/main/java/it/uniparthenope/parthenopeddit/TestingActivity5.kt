package it.uniparthenope.parthenopeddit

import android.os.Bundle
import android.widget.Toast
import it.uniparthenope.parthenopeddit.api.requests.ReviewsRequests
import kotlinx.android.synthetic.main.activity_testing5.*

class TestingActivity5 : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing5)

        button_test_1.setOnClickListener {
            try {
                ReviewsRequests(this, app.auth).publishNewReview(
                    input_test_1.text.toString(),
                    1,
                    input_test_2.text.toString().toInt(),
                    input_test_3.text.toString().toInt(),
                    {
                        output_test_1.text = it.id.toString()
                    }, {
                        output_test_1.text = it
                    }
                )
            } catch (e:Exception) {
            }
        }

        button_test_2.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                ReviewsRequests(this, app.auth).getReview(
                    id,
                    {
                        output_test_2.text = it.body
                    }, {
                        output_test_2.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need review id", Toast.LENGTH_SHORT).show()
            }
        }

        button_test_3.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                ReviewsRequests(this, app.auth).getReviewWithComments(
                    id,
                    {
                        output_test_3.text = it.comments_num.toString()
                    }, {
                        output_test_3.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need review id", Toast.LENGTH_SHORT).show()
            }
        }

        button_test_4.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                ReviewsRequests(this, app.auth).likeReview(
                    id,
                    {
                        output_test_4.text = "LIKED REVIEW $id"
                    }, {
                        output_test_4.text = "REMOVED LIKE FROM REVIEW $id"
                    }, {
                        output_test_4.text = "REMOVED DISLIKE AND LIKED REVIEW $id"
                    }, {
                        output_test_4.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need review id", Toast.LENGTH_SHORT).show()
            }
        }

        button_test_5.setOnClickListener {
            try {
                val id = input_test_1.text.toString().toInt()
                ReviewsRequests(this, app.auth).dislikeReview(
                    id,
                    {
                        output_test_5.text = "DISLIKED REVIEW $id"
                    }, {
                        output_test_5.text = "REMOVED DISLIKE FROM REVIEW $id"
                    }, {
                        output_test_5.text = "REMOVED LIKE AND DISLIKED REVIEW $id"
                    }, {
                        output_test_5.text = it
                    }
                )
            } catch (e:Exception) {
                Toast.makeText(this, "Need review id", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
