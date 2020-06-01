package it.uniparthenope.parthenopeddit.android

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.cardview_post.view.*

class CommentActivity : BasicActivity(), CommentAdapter.CommentItemClickListeners {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val extras = intent.extras
        var id_post:Int = extras?.getInt("idPost")?:0
        if(id_post == 0) finish()

        PostsRequests(this, app.auth).getPostWithComments(
            id_post,
            { post ->
                val username_textview: TextView = findViewById(R.id.username_textview)
                val titolo: TextView = findViewById(R.id.title_textview)
                val body: TextView = findViewById(R.id.posttext_textview)
                val imageView: ImageView = findViewById(R.id.image_view)
                val board_textview: TextView = findViewById(R.id.board_textview)
                val timestamp_textview: TextView = findViewById(R.id.timestamp_textview)
                val upvote_textview: TextView = findViewById(R.id.upvote_textview)
                val downvote_textview: TextView = findViewById(R.id.downvote_textview)
                val comments_textview: TextView = findViewById(R.id.downvote_textview)

                username_textview.text = post.author?.display_name?:post.author_id
                titolo.text = post.title
                body.text = post.body
                imageView.setImageResource(R.drawable.default_user_image)
                board_textview.text = post.posted_to_board?.name?:"Generale"
                timestamp_textview.text = post.timestamp
                upvote_textview.text = post.likes_num.toString()
                downvote_textview.text = post.dislikes_num.toString()
                comments_textview.text = post.comments_num.toString()

                if( post.posted_to_board == null || post.posted_to_board_id == null || post.posted_to_board_id == 0 ) {
                    board_textview.setBackgroundResource(R.drawable.general_textview_bubble)
                    board_textview.setTextColor(Color.BLACK)
                } else {
                    when (post.posted_to_board!!.type) {
                        "course" -> board_textview.setBackgroundResource(R.drawable.fab_textview_bubble)
                        "group" -> board_textview.setBackgroundResource(R.drawable.group_textview_bubble)
                        else -> board_textview.visibility = View.GONE
                    }
                }

                val commenti = post.comments
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

        var message_edittext = findViewById<EditText>(R.id.message_edittext)
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
