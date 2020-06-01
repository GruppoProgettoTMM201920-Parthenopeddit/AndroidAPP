package it.uniparthenope.parthenopeddit.model

import android.content.BroadcastReceiver
import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Message(
    val id: Int,
    val body: String,
    val timestamp: String,

    val replies_to_message_id: Int? = null,
    val sender_id: String,
    val receiver_id: Int
) : JSONConvertable {

    var sender_uder: User? = null
    var receiver_chat: Chat? = null
    var replies_to_message: Message? = null
}