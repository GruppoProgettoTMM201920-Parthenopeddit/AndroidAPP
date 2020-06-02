package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.android.view.CardviewPost
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toObject
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : BasicActivity(), CommentAdapter.CommentItemClickListeners {

    private lateinit var imageView: ImageView
    private lateinit var username_textview: TextView
    private lateinit var titolo: TextView
    private lateinit var body: TextView
    private lateinit var board_textview: TextView
    private lateinit var timestamp_textview: TextView
    private lateinit var upvote_textview: TextView
    private lateinit var downvote_textview: TextView
    private lateinit var comments_textview: TextView
    private lateinit var upvote_btn: ImageButton
    private lateinit var downvote_btn: ImageButton
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var cardviewPost: CardviewPost

    private lateinit var post:Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val cardviewPost: CardviewPost = findViewById(R.id.post)

        val extras = intent.extras
        val id_post:Int = extras?.getInt("idPost")?:0
        if(id_post == 0) finish()
        val extrasPost: Post? = extras?.getString("post")?.toObject()

        if(extrasPost != null) {
            cardviewPost.setPost(extrasPost)
        }

        PostsRequests(this, app.auth).getPostWithComments(
            id_post,
            { fetched_post ->
                cardviewPost.setPost(fetched_post)

                val commenti = fetched_post.comments
                if(commenti == null) {
                    listaCommenti.visibility = View.GONE
                } else {
                    listaCommenti.visibility = View.VISIBLE

                    val listaCommenti:RecyclerView = findViewById(R.id.listaCommenti)
                    val commentAdapter = CommentAdapter(this, commenti, this)
                    listaCommenti.adapter = commentAdapter
                    listaCommenti.layoutManager = LinearLayoutManager(this)
                    listaCommenti.setHasFixedSize(true)
                }
            }, {
                Toast.makeText(this, "errore : $it", Toast.LENGTH_LONG).show()
            }
        )

        val message_edittext = findViewById<EditText>(R.id.message_edittext)
        val send_btn = findViewById<ImageButton>(R.id.send_btn)
        var message: String

        send_btn.setOnClickListener {
            message = message_edittext.text.toString()
            if(message.isNotEmpty()){
                //TODO: Send message through API
            } else{
                Toast.makeText(this,"Non hai scritto alcun commento.",Toast.LENGTH_SHORT).show()
            }
        }

        cardviewPost.setListeners(
            object : CardviewPost.PostItemClickListeners {
                override fun onPostClick(post: Post) {}

                override fun onClickComments(post: Post) {}

                override fun onClickLike(
                    id_post: Int,
                    upvoteTextView: TextView,
                    downvoteTextView: TextView
                ) {
                    PostsRequests(this@CommentActivity, app.auth).likePost(
                        id_post,
                        {
                            /*Like piazzato */
                            upvoteTextView.text = it.likes_num.toString()
                            downvoteTextView.text = it.dislikes_num.toString()
                        }, {
                            /*Like rimosso */
                            upvoteTextView.text = it.likes_num.toString()
                            downvoteTextView.text = it.dislikes_num.toString()
                        }, {
                            /* dislike rimosso e piazzato like */
                            upvoteTextView.text = it.likes_num.toString()
                            downvoteTextView.text = it.dislikes_num.toString()
                        }, {
                        }
                    )
                }

                override fun onClickDislike(
                    id_post: Int,
                    upvoteTextView: TextView,
                    downvoteTextView: TextView
                ) {
                    PostsRequests(this@CommentActivity, app.auth).dislikePost(
                        id_post,
                        {
                            /*disLike piazzato */
                            upvoteTextView.text = it.likes_num.toString()
                            downvoteTextView.text = it.dislikes_num.toString()
                        }, {
                            /*disLike rimosso */
                            upvoteTextView.text = it.likes_num.toString()
                            downvoteTextView.text = it.dislikes_num.toString()
                        }, {
                            /* like rimosso e piazzato disLike */
                            upvoteTextView.text = it.likes_num.toString()
                            downvoteTextView.text = it.dislikes_num.toString()
                        }, {
                        }
                    )
                }

                override fun onBoardClick(board_id: Int?, board: Board?) {
                    if (board == null) {
                        goToActivity(HomeActivity::class.java) //HOME
                    } else {
                        when (board.type) {
                            "course" -> {
                                val intent = Intent(this@CommentActivity, CourseActivity::class.java)  //CORSO
                                intent.putExtra("id_group", board_id)
                                startActivity(intent)
                            }
                            "group" -> {
                                val intent = Intent(this@CommentActivity, GroupActivity::class.java)
                                intent.putExtra("id_group", board_id)
                                startActivity(intent)
                            }
                            else -> {  }
                        }
                    }
                }

            }
        )
    }

    override fun onClickLike(id_Commento: Int) {
    }

    override fun onClickDislike(id_Commento: Int) {
    }

    override fun onClickComments(id_Commento: Int) {
    }

    override fun onCommentClick(id_post: Int) {
        //must do nothing to not be recursive
    }
}
