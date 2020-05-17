package it.uniparthenope.parthenopeddit.model
import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Content (
    val id: Int,
    val body: String? = null,
    val timestamp: String, /* TODO STRING type as DateTime decode not yet implemented */
    val author_id: String,

    /* relationships */
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,

    /* aggregated data */
    var comments_num: Int? = null,
    var upvotes_num: Int,
    var downvotes_num: Int
) : JSONConvertable