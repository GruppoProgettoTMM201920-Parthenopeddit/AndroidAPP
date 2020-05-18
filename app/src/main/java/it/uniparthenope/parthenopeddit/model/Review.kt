package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Review(
    val id: Int,
    val body: String,
    val timestamp: String,
    val author_id: String,
    val reviewed_course_id: Int,
    val score_liking: Int,
    val score_difficulty: Int,

    /* relationships */
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,
    var reviewed_course: Course?,

    /* aggregated data */
    var comments_num: Int? = null,
    var likes_num: Int? = null,
    var dislikes_num: Int? = null
) : Content(), JSONConvertable