package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class Comment(
    val id: Int,
    val body: String,
    val timestamp: String,
    val author_id: String,
    val commented_content_id: Int,

    /* relationships */
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,
    var commented_content: Content?,

    /* aggregated data */
    var comments_num: Int? = null,
    var likes_num: Int? = null,
    var dislikes_num: Int? = null
) : Content(), JSONConvertable