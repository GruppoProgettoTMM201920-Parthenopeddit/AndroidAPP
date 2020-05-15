package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class Post (
    val id: Int,
    val title: String,
    val body: String?,
    val timestamp: String, /* TODO STRING type as DaetTime decode not yet implemented */
    val author_id: String,

    /* relationships */
    val author: User?,
    val comments: List<Comment>?,

    /* aggregated data */
    val comments_num: Int?
) : JSONConvertable