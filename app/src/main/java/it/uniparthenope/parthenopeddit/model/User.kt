package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

//TODO implement
data class User (
    val id: String,
    val nome_visualizzato: String? = null,
    val registrato_il: String, /* TODO STRING type as DaetTime decode not yet implemented */

    /* relationships */
    var posts: List<Post>? = null,
    var comments: List<Comment>? = null,

    /* aggregated data */
    var posts_num: Int? = null,
    var comments_num: Int? = null
) : JSONConvertable