package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable

class GroupMember(
    val user_id: String,
    val group_id: Int,
    val join_date: String,
    val last_read_chat: String,
    val is_owner: Boolean
) : JSONConvertable {
    var user: User? = null
    var group: Group? = null
}