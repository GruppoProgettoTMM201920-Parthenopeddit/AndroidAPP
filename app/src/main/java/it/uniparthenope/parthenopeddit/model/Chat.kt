package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Chat(
    val id: Int,
    val type: String,
    var received_messages: ArrayList<Message>? = null,
    var latest_message: Message? = null
) : JSONConvertable