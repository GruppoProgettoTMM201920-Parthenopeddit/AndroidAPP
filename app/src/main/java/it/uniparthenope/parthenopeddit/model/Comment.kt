package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Comment(
    id: Int,
    body: String,
    timestamp: String,
    author_id: String,

    val commented_content_id: Int,
    val root_content_id: Int,

    /* relationships */
    author: User? = null,
    comments: ArrayList<Comment>? = null,
    var commented_content: Content? = null,
    var root_content: Content? = null,

    /* aggregated data */
    comments_num: Int? = null,
    likes_num: Int? = null,
    dislikes_num: Int? = null

) : Content(
    id, body, timestamp, author_id,
    "comment",
    author, comments, comments_num, likes_num, dislikes_num
), JSONConvertable
