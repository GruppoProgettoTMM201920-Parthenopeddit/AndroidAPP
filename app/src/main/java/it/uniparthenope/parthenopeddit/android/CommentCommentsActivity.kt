package it.uniparthenope.parthenopeddit.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CommentRecursiveAdapter
import it.uniparthenope.parthenopeddit.api.requests.CommentsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.util.DateParser
import it.uniparthenope.parthenopeddit.util.toGson
import it.uniparthenope.parthenopeddit.util.toObject
import kotlinx.android.synthetic.main.activity_post_comments.*
import kotlinx.android.synthetic.main.cardview_commento.*
import kotlinx.android.synthetic.main.cardview_post.comments_btn
import kotlinx.android.synthetic.main.cardview_post.downvote_btn
import kotlinx.android.synthetic.main.cardview_post.downvote_textview
import kotlinx.android.synthetic.main.cardview_post.image_view
import kotlinx.android.synthetic.main.cardview_post.posttext_textview
import kotlinx.android.synthetic.main.cardview_post.upvote_btn
import kotlinx.android.synthetic.main.cardview_post.upvote_textview
import kotlinx.android.synthetic.main.cardview_post.username_textview

class CommentCommentsActivity : LoginRequiredActivity(), CommentRecursiveAdapter.CommentItemClickListeners {

    private lateinit var comment:Comment
    private lateinit var adapter: CommentRecursiveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_comments)

        val extras = intent.extras
        val id_comment:Int = extras?.getInt("idComment")?:0


        if(id_comment == 0) finish()

        var deserializedComment:Comment? = null

        try {
            deserializedComment = (extras?.getString("comment")?:"").toObject()
        } catch(e:Exception) {}


        if( deserializedComment != null ) {
            setComment(deserializedComment)
        }

        adapter = CommentRecursiveAdapter(this,this)

        ViewCompat.setNestedScrollingEnabled(listaCommenti, false)

        CommentsRequests(this, app.auth).getCommentWithComments(id_comment,
            {
                Log.d("DEBUG","Fetched post ${id_comment}")

                setComment(it)

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

        val message_edittext = findViewById<EditText>(R.id.message_edittext)
        val send_btn = findViewById<ImageButton>(R.id.send_btn)
        var message: String

        send_btn.setOnClickListener {
            message = message_edittext.text.toString()
            if(message.isNotEmpty()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                CommentsRequests(this, app.auth).publishNewComment(
                    message,
                    comment.id,
                    {
                        adapter.aggiungiCommenti(listOf(it))
                        message_edittext.text.clear()
                    }, {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                )
            } else{
                Toast.makeText(this,"Non hai scritto alcun commento.",Toast.LENGTH_SHORT).show()
            }
        }

        val itemsswipetorefresh: MySwipeRefreshLayout = findViewById(R.id.itemsswipetorefresh)

        itemsswipetorefresh.child = commentsScrollContainer

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.white))
        itemsswipetorefresh.setOnRefreshListener {
            itemsswipetorefresh.isRefreshing = true

            CommentsRequests(this, app.auth).getCommentWithComments(id_comment,
                {
                    setComment(it)

                    val commenti = it.comments
                    if(commenti == null) {
                        listaCommenti.visibility = View.GONE
                    } else {
                        listaCommenti.visibility = View.VISIBLE

                        val listaCommenti:RecyclerView = findViewById(R.id.listaCommenti)

                        adapter.aggiornaLista(commenti)

                        listaCommenti.adapter = adapter
                        listaCommenti.layoutManager = LinearLayoutManager(this)
                        listaCommenti.setHasFixedSize(true)
                    }

                    itemsswipetorefresh.isRefreshing = false
                }, {
                    //nothing
                    itemsswipetorefresh.isRefreshing = false
                }
            )
        }
    }

    private fun setComment(newComment: Comment) {
        comment = newComment


        image_view.setImageResource(R.drawable.default_user_image)
        username_textview.text = comment.author?.display_name?:comment.author_id
        timestamp_comment_textview.text = DateParser.prettyParse(comment.timestamp)
        posttext_textview.text = comment.body
        upvote_textview.text = comment.likes_num.toString()
        downvote_textview.text = comment.dislikes_num.toString()
        comment_comments_textview.text = comment.comments_num.toString()
        commentsLayoutContainer.visibility = View.GONE

        upvote_btn.setOnClickListener {
            onClickLike(comment.id, upvote_textview, downvote_textview)
        }

        downvote_btn.setOnClickListener {
            onClickDislike(comment.id, upvote_textview, downvote_textview)
        }

        comments_btn.setOnClickListener {
            //nothing
        }

        username_textview.setOnClickListener {
            onUserClick(comment.author_id)
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
