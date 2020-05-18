package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Post(
    val id: Int,
    val body: String,
    val timestamp: String,
    val author_id: String,
    val title: String,
    val posted_to_board_id: Int,

    /* relationships */
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,
    var posted_to_board: Board?,

    /* aggregated data */
    var comments_num: Int? = null,
    var likes_num: Int? = null,
    var dislikes_num: Int? = null
) : Content(), JSONConvertable