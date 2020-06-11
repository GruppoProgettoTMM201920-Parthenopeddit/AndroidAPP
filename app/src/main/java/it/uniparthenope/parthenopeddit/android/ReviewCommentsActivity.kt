package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.api.requests.CommentsRequests
import it.uniparthenope.parthenopeddit.api.requests.ReviewsRequests
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Review
import it.uniparthenope.parthenopeddit.util.DateParser
import it.uniparthenope.parthenopeddit.util.toGson
import it.uniparthenope.parthenopeddit.util.toObject
import kotlinx.android.synthetic.main.activity_post_comments.*
import kotlinx.android.synthetic.main.cardview_post.downvote_btn
import kotlinx.android.synthetic.main.cardview_post.downvote_textview
import kotlinx.android.synthetic.main.cardview_post.image_view
import kotlinx.android.synthetic.main.cardview_post.posttext_textview
import kotlinx.android.synthetic.main.cardview_post.timestamp_textview
import kotlinx.android.synthetic.main.cardview_post.upvote_btn
import kotlinx.android.synthetic.main.cardview_post.upvote_textview
import kotlinx.android.synthetic.main.cardview_post.username_textview
import kotlinx.android.synthetic.main.cardview_review.*

class ReviewCommentsActivity : LoginRequiredActivity(), CommentAdapter.CommentItemClickListeners {

    private lateinit var review: Review
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_comments)

        val extras = intent.extras
        val id_review:Int = extras?.getInt("idReview")?:0

        if(id_review == 0) finish()

        var deserializedReview:Review? = null

        try {
            deserializedReview = (extras?.getString("review")?:"").toObject()
        } catch(e:Exception) {}


        if( deserializedReview != null ) {
            setReview(deserializedReview)
        }

        adapter = CommentAdapter(this,this)

        ReviewsRequests(this, app.auth).getReviewWithComments(id_review,
            {
                setReview(it)

                val commenti = it.comments
                if(commenti == null) {
                    listaCommenti.visibility = View.GONE
                } else {
                    listaCommenti.visibility = View.VISIBLE

                    Log.d("DEBUG","initializing comments layout")

                    val listaCommenti:RecyclerView = findViewById(R.id.listaCommenti)

                    adapter.aggiornaLista(commenti)

                    listaCommenti.adapter = adapter
                    listaCommenti.layoutManager = LinearLayoutManager(this)
                    listaCommenti.setHasFixedSize(true)

                    Log.d("DEBUG","done")
                }
            }, {
                //nothing
            }
        )

        var message_edittext = findViewById<EditText>(R.id.message_edittext)
        val send_btn = findViewById<ImageButton>(R.id.send_btn)
        var message: String

        send_btn.setOnClickListener {
            message = message_edittext.text.toString()
            if(message.isNotEmpty()) {
                CommentsRequests(this, app.auth).publishNewComment(
                    message,
                    review.id,
                    {
                        adapter.aggiungiCommenti(listOf(it))
                    }, {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                )
            } else{
                Toast.makeText(this,"Non hai scritto alcun commento.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setReview(newReview: Review) {
        review = newReview


        image_view.setImageResource(R.drawable.default_user_image)
        username_textview.text = review.author?.display_name?:review.author_id
        timestamp_textview.text = DateParser.parse(review.timestamp)
        posttext_textview.text = review.body
        upvote_textview.text = review.likes_num.toString()
        downvote_textview.text = review.dislikes_num.toString()
        comments_textview2.text = review.comments_num.toString()
        liking_rating_bar.rating = review.score_liking.toFloat()
        difficulty_rating_bar.rating = review.score_difficulty.toFloat()

        upvote_btn.setOnClickListener {
            ReviewsRequests(this, app.auth).likeReview(
                review.id, {
                    updateLike(upvote_textview, downvote_textview, it)
                }, {
                    updateLike(upvote_textview, downvote_textview, it)
                }, {
                    updateLike(upvote_textview, downvote_textview, it)
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            )
        }

        downvote_btn.setOnClickListener {
            ReviewsRequests(this, app.auth).dislikeReview(
                review.id, {
                    updateLike(upvote_textview, downvote_textview, it)
                }, {
                    updateLike(upvote_textview, downvote_textview, it)
                }, {
                    updateLike(upvote_textview, downvote_textview, it)
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            )
        }

        review_relativelayout.setOnClickListener {
            //nothing
        }

        comments_btn2.setOnClickListener {
            //nothing
        }

        username_textview.setOnClickListener {
            onUserClick(review.author_id)
        }
    }

    private fun updateLike(upvote_textview: TextView, downvote_textview: TextView, scores: LikeDislikeScore) {
        upvote_textview.text = scores.likes_num.toString()
        downvote_textview.text = scores.dislikes_num.toString()
    }

    override fun onClickLike(
        id_Commento: Int,
        upvote_textview: TextView,
        downvote_textview: TextView
    ) {
        CommentsRequests(this, app.auth).likeComment(
            id_Commento,
            {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                //nothing
            }
        )
    }

    override fun onClickDislike(
        id_Commento: Int,
        upvote_textview: TextView,
        downvote_textview: TextView
    ) {
        CommentsRequests(this, app.auth).dislikeComment(
            id_Commento,
            {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                //nothing
            }
        )
    }

    override fun onClickComments(id_Commento: Int, comment: Comment) {
        val intent = Intent(this, CommentCommentsActivity::class.java)
        intent.putExtra("idComment", id_Commento)
        intent.putExtra("comment", comment.toGson())
        startActivity(intent)
    }

    override fun onUserClick(id_user: String) {
        val intent = Intent(this, UserProfileActivity::class.java)
        intent.putExtra("id_user", id_user)
        startActivity(intent)
    }
}
