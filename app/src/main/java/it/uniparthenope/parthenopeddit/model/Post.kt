package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Post(
    id: Int,
    body: String,
    timestamp: String,
    author_id: String,
    author: User? = null,
    comments: ArrayList<Comment>? = null,
    comments_num: Int? = null,
    likes_num: Int? = null,
    dislikes_num: Int? = null,

    val title: String,
    val posted_to_board_id: Int? = null,
    var posted_to_board: Board? = null

) : Content(
    id, body, timestamp, author_id,
    "post",
    author, comments, comments_num, likes_num, dislikes_num
), JSONConvertable