package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

//TODO implement
data class Comment(
    val id: Int,
    val body: String,
    val timestamp: String, /* TODO STRING type as DaetTime decode not yet implemented */
    val author_id: String,
    val commented_post_id: Int? = null,
    val commented_comment_id: Int? = null,

    /* relationships */
    var author: User? = null,
    var commented_post: Post? = null,
    var commented_comment: Comment? = null,
    var comments: List<Comment>? = null,

    /* aggregated data */
    var comments_num: Int? = null
) : JSONConvertable