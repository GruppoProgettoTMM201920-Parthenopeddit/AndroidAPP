package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable

class GroupChat(
    id: Int,
    received_messages: ArrayList<Message>? = null,

    val of_group_id: Int,
    var of_group: Group? = null
) : Chat(
    id,
    "group_chat",
    received_messages
), JSONConvertable