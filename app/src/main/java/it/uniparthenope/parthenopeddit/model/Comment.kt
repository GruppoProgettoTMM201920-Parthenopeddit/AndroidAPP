package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

//TODO implement
data class Comment(
    val id: Int,
    val body: String,
    val timestamp: String, /* TODO STRING type as DaetTime decode not yet implemented */
    val author_id: String,
    val commented_post_id: Int?,
    val commented_comment_id: Int?,

    /* relationships */
    val author: User?,
    val commented_post: Post?,
    val commented_comment: Comment?,
    val comments: List<Comment>?,

    /* aggregated data */
    val comments_num: Int?
) : JSONConvertable