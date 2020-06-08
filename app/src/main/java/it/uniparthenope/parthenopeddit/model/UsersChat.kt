package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class UsersChat(
    id: Int,

    val of_user_id: String,
    val last_opened_on: String,
    val other_user_chat_id: Int,
    var of_user: User? = null,
    var other_user_chat: UsersChat? = null,

    var last_message: Message? = null,
    var chat_log: ArrayList<Message>? = null
) : Chat(
    id,
    "users_chat"
), JSONConvertable