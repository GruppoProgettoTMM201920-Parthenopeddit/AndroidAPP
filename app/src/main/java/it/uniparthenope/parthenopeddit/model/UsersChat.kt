package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class UsersChat(
    id: Int,
    received_messages: ArrayList<Message>? = null,
    latest_message: Message? = null,

    val of_user_id: String,
    val last_opened_on: String,
    val other_user_chat_id: Int,
    var of_user: User? = null,
    var other_user_chat: UsersChat? = null
) : Chat(
    id,
    "users_chat",
    received_messages,
    latest_message
), JSONConvertable