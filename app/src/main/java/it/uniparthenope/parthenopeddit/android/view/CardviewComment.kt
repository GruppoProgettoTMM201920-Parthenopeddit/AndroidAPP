package it.uniparthenope.parthenopeddit.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Comment

//TODO DA FINIRE


class CardviewComment(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    // override all constructors to ensure custom logic runs in all cases
    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    private lateinit var comment: Comment

    private var imageView: ImageView
    private var username_textview: TextView
    private var body: TextView
    private var timestamp_textview: TextView
    private var upvote_textview: TextView
    private var downvote_textview: TextView
    private var comments_textview: TextView
    private var upvote_btn: ImageButton
    private var downvote_btn: ImageButton
    private var comment_btn: ImageButton
    private var relativeLayout: RelativeLayout

    interface CommentItemLikeClickListener {
        fun onClickLike(id_comment:Int, upvoteTextView: TextView, downvoteTextView: TextView)
    }
    interface CommentItemDislikeClickListener {
        fun onClickDislike(id_comment:Int, upvoteTextView: TextView, downvoteTextView: TextView)
    }
    interface CommentItemCommentClickListener {
        fun onClickComments(comment: Comment)
    }
    interface CommentItemClickListener {
        fun onCommentClick(comment: Comment)
    }

    interface CommentItemClickListeners :
        CommentItemLikeClickListener,
        CommentItemDislikeClickListener,
        CommentItemCommentClickListener,
        CommentItemClickListener

    var likeListener: CommentItemLikeClickListener? = null
    var dislikeListener: CommentItemDislikeClickListener? = null
    var commentListener: CommentItemCommentClickListener? = null
    var postListener: CommentItemClickListener? = null

    fun setListeners(listener: CommentItemClickListeners?) {
        likeListener = listener
        dislikeListener = listener
        commentListener = listener
        postListener = listener
    }

    fun setComment(newComment: Comment) {
        comment = newComment

        username_textview.text = comment.author?.display_name?:comment.author_id
        body.text = comment.body
        imageView.setImageResource(R.drawable.default_user_image)
        timestamp_textview.text = comment.timestamp
        upvote_textview.text = comment.likes_num.toString()
        downvote_textview.text = comment.dislikes_num.toString()
        comments_textview.text = comment.comments_num.toString()

        upvote_btn.setOnClickListener {
            likeListener?.onClickLike(comment.id, upvote_textview, downvote_textview)
        }

        downvote_btn.setOnClickListener {
            dislikeListener?.onClickDislike(comment.id, upvote_textview, downvote_textview)
        }

        comment_btn.setOnClickListener {
            commentListener?.onClickComments(comment)
        }

        relativeLayout.setOnClickListener {
            postListener?.onCommentClick(comment)
        }
    }

    init {
        View.inflate(getContext(), R.layout.cardview_commento, this)

        username_textview = findViewById(R.id.username_textview)
        body = findViewById(R.id.body)
        imageView = findViewById(R.id.image_view)
        timestamp_textview = findViewById(R.id.timestamp_textview)
        upvote_textview = findViewById(R.id.upvote_textview)
        downvote_textview = findViewById(R.id.downvote_textview)
        comments_textview = findViewById(R.id.comment_comments_textview)
        upvote_btn = findViewById(R.id.upvote_btn)
        downvote_btn = findViewById(R.id.downvote_btn)
        comment_btn = findViewById(R.id.comments_btn)
        relativeLayout = findViewById(R.id.comment_relativelayout)
    }
}