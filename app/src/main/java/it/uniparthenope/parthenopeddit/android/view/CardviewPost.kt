package it.uniparthenope.parthenopeddit.android.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post


class CardviewPost(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    // override all constructors to ensure custom logic runs in all cases
    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    private lateinit var post: Post

    private var imageView: ImageView
    private var username_textview: TextView
    private var titolo: TextView
    private var body: TextView
    private var board_textview: TextView
    private var timestamp_textview: TextView
    private var upvote_textview: TextView
    private var downvote_textview: TextView
    private var comments_textview: TextView
    private var upvote_btn: ImageButton
    private var downvote_btn: ImageButton
    private var comment_btn: ImageButton
    private var relativeLayout: RelativeLayout

    interface PostItemLikeClickListener {
        fun onClickLike(id_post:Int, upvoteTextView: TextView, downvoteTextView: TextView)
    }
    interface PostItemDislikeClickListener {
        fun onClickDislike(id_post:Int, upvoteTextView: TextView, downvoteTextView: TextView)
    }
    interface PostItemCommentClickListener {
        fun onClickComments(post: Post)
    }
    interface PostItemBoardClickListener {
        fun onBoardClick(board_id: Int?, board: Board?)
    }
    interface PostItemClickListener {
        fun onPostClick(post: Post)
    }

    interface PostItemClickListeners :
        PostItemClickListener,
        PostItemLikeClickListener,
        PostItemDislikeClickListener,
        PostItemCommentClickListener,
        PostItemBoardClickListener

    var likeListener: PostItemLikeClickListener? = null
    var dislikeListener: PostItemDislikeClickListener? = null
    var commentListener: PostItemCommentClickListener? = null
    var boardListener: PostItemBoardClickListener? = null
    var postListener: PostItemClickListener? = null

    fun setListeners(listener: PostItemClickListeners?) {
        likeListener = listener
        dislikeListener = listener
        commentListener = listener
        boardListener = listener
        postListener = listener
    }

    fun setPost(newPost: Post) {
        post = newPost

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

        upvote_btn.setOnClickListener {
            likeListener?.onClickLike(post.id, upvote_textview, downvote_textview)
        }

        downvote_btn.setOnClickListener {
            dislikeListener?.onClickDislike(post.id, upvote_textview, downvote_textview)
        }

        comment_btn.setOnClickListener {
            commentListener?.onClickComments(post)
        }

        relativeLayout.setOnClickListener {
            postListener?.onPostClick(post)
        }

        board_textview.setOnClickListener {
            boardListener?.onBoardClick(post.posted_to_board_id, post.posted_to_board)
        }
    }

    init {
        View.inflate(getContext(), R.layout.cardview_post, this)

        username_textview = findViewById(R.id.username_textview)
        titolo = findViewById(R.id.title_textview)
        body = findViewById(R.id.body)
        imageView = findViewById(R.id.image_view)
        board_textview = findViewById(R.id.board_textview)
        timestamp_textview = findViewById(R.id.timestamp_textview)
        upvote_textview = findViewById(R.id.upvote_textview)
        downvote_textview = findViewById(R.id.downvote_textview)
        comments_textview = findViewById(R.id.comment_comments_textview)
        upvote_btn = findViewById(R.id.upvote_btn)
        downvote_btn = findViewById(R.id.downvote_btn)
        comment_btn = findViewById(R.id.comments_btn)
        relativeLayout = findViewById(R.id.post_relativelayout)
    }
}