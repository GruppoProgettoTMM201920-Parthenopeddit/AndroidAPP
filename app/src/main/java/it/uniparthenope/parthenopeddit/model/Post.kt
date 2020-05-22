package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class Post (
    val id: Int,
    val title: String,
    val body: String? = null,
    val timestamp: String, /* TODO STRING type as DateTime decode not yet implemented */
    val author_id: String,

    /* relationships */
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,
    var group: String? = null,
    var group_type: Int = 0,        //0: GENERALE - 1: CORSO - 2: GRUPPO

    /* aggregated data */
    var comments_num: Int? = null
) : JSONConvertable