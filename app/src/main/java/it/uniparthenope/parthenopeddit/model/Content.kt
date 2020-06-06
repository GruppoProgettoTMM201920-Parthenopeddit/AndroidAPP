package it.uniparthenope.parthenopeddit.model
import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Content (
    val id: Int,
    val body: String? = null,
    val timestamp: String,
    val author_id: String,
    val type: String,

    /* relationships */
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,

    /* aggregated data */
    var comments_num: Int? = null,
    var likes_num: Int? = null,
    var dislikes_num: Int? = null
) : JSONConvertable

class LikeDislikeScore(
    val likes_num: Int,
    val dislikes_num: Int
) : JSONConvertable
