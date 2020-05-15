package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class Post (
    val id: Int,
    val title: String,
    val body: String? = null,
    val timestamp: String, /* TODO STRING type as DaetTime decode not yet implemented */
    val author_id: String,

    /* relationships */
    var author: User? = null,
    var comments: List<Comment>? = null,

    /* aggregated data */
    var comments_num: Int? = null
) : JSONConvertable