package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

//TODO implement
data class User (
    val id: String,
    val nome_visualizzato: String?,
    val registrato_il: String, /* TODO STRING type as DaetTime decode not yet implemented */

    /* relationships */
    val posts: List<Post>?,
    val comments: List<Comment>?,

    /* aggregated data */
    val posts_num: Int?,
    val comments_num: Int?
) : JSONConvertable