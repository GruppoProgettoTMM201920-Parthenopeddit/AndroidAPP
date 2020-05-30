package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList


class Review(
    id: Int,
    body: String,
    timestamp: String,
    author_id: String,
    author: User? = null,
    comments: ArrayList<Comment>? = null,
    comments_num: Int? = null,
    likes_num: Int? = null,
    dislikes_num: Int? = null,

    val reviewed_course_id: Int,
    val score_liking: Int,
    val score_difficulty: Int,
    var reviewed_course: Course? = null

) : Content(
    id, body, timestamp, author_id,
    "review",
    author, comments, comments_num, likes_num, dislikes_num
), JSONConvertable