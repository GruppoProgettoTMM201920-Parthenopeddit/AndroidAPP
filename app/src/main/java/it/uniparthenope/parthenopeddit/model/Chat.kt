package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class Chat (
    val user_id: Int,
    val username: String,
    val last_message: String? = null,
    val date: String,

    /* relationships */
    var author: User? = null

) : JSONConvertable