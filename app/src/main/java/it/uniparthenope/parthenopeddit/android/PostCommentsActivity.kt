package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.graphics.Color
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
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.DateParser
import it.uniparthenope.parthenopeddit.util.toGson
import it.uniparthenope.parthenopeddit.util.toObject
import kotlinx.android.synthetic.main.activity_post_comments.*
import kotlinx.android.synthetic.main.cardview_post.*

class PostCommentsActivity : LoginRequiredActivity(), CommentAdapter.CommentItemClickListeners {

    private lateinit var post:Post
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comments)

        val extras = intent.extras
        val id_post:Int = extras?.getInt("idPost")?:0

        if(id_post == 0) finish()

        var deserializedPost:Post? = null

        try {
            deserializedPost = (extras?.getString("post")?:"").toObject()
        } catch(e:Exception) {}


        if( deserializedPost != null ) {
            setPost(deserializedPost)
        }

        adapter = CommentAdapter(this,this)

        PostsRequests(this, app.auth).getPostWithComments(id_post,
            {
                Log.d("DEBUG","Fetched post ${id_post}")

                setPost(it)

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
                    post.id,
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

    private fun setPost(newPost: Post) {
        post = newPost

        image_view.setImageResource(R.drawable.default_user_image)
        username_textview.text = post.author?.display_name?:post.author_id
        title_textview.text = post.title
        group_textview.text = post.posted_to_board?.name?:"Generale"
        timestamp_textview.text = DateParser.parse(post.timestamp)
        posttext_textview.text = post.body
        upvote_textview.text = post.likes_num.toString()
        downvote_textview.text = post.dislikes_num.toString()
        comments_textview.text = post.comments_num.toString()

        if( post.posted_to_board_id == 0 || post.posted_to_board_id == null ) {
            group_textview.setBackgroundResource(R.drawable.general_textview_bubble)
            group_textview.setTextColor(Color.BLACK)
        } else {
            when (post.posted_to_board!!.type) {
                "course" -> group_textview.setBackgroundResource(R.drawable.fab_textview_bubble)
                "group" -> group_textview.setBackgroundResource(R.drawable.group_textview_bubble)
                else -> group_textview.visibility = View.GONE
            }
        }

        upvote_btn.setOnClickListener {
            PostsRequests(this, app.auth).likePost(
                post.id, {
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
            PostsRequests(this, app.auth).dislikePost(
                post.id, {
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

        comments_btn.setOnClickListener {
            //nothing
        }

        post_relativelayout.setOnClickListener {
            //nothing
        }

        group_textview.setOnClickListener {
            if (post.posted_to_board_id == null || post.posted_to_board_id == 0) {
                goToActivity(HomeActivity::class.java) //HOME
            } else {
                when (post.posted_to_board?.type) {
                    "course" -> {
                        val intent = Intent(this, CourseActivity::class.java)  //CORSO
                        intent.putExtra("id_course", post.posted_to_board_id!!)
                        startActivity(intent)
                    }
                    "group" -> {
                        val intent = Intent(this, GroupActivity::class.java)
                        intent.putExtra("id_group", post.posted_to_board_id!!)
                        startActivity(intent)
                    }
                    else -> {  }
                }
            }
        }

        username_textview.setOnClickListener {
            onUserClick(post.author_id)
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
