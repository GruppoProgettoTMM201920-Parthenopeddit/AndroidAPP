package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable

class GroupChat(
    id: Int,

    var latest_message: String? = null,
    var user_latestmessage: String? = null,
    val of_group_id: Int,
    var of_group: Group? = null
) : Chat(id, "group_chat"), JSONConvertable {
}