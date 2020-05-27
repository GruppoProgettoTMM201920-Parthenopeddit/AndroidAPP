package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class User (
    val id: String,
    val display_name: String? = null,
    val registered_on: String, /* TODO STRING type as DateTime decode not yet implemented */

    /* relationships */
    var posts: ArrayList<Post>? = null,
    var comments: ArrayList<Comment>? = null,

    /* aggregated data */
    var posts_num: Int? = null,
    var comments_num: Int? = null
) : JSONConvertable