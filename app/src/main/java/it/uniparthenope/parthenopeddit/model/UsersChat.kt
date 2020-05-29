package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable

class UsersChat(
    id: Int,

    val of_user_id: String,
    val last_opened_on: String,
    val other_user_chat_id: Int
) : Chat(id, "users_chat"), JSONConvertable {

    var of_user: User? = null
    var other_user_chat: UsersChat? = null
}