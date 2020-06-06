package it.uniparthenope.parthenopeddit.model

import android.content.BroadcastReceiver
import it.uniparthenope.parthenopeddit.util.JSONConvertable

class GroupMessage(
    val id: Int,
    val body: String,
    val timestamp: String,

    val replies_to_message_id: Int? = null,
    val sender_id: String,
    val receiver_id: Int,

    @Transient var sender_user: User? = null,
    @Transient var receiver_groupchat: GroupChat? = null,
    @Transient var replies_to_message: Message? = null
) : JSONConvertable