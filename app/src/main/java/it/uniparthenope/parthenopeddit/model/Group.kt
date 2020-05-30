package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList

class Group(
    id: Int,
    name: String,

    val created_on: String
) : Board(id, name, "group"), JSONConvertable {

    var members: ArrayList<GroupMember>? = null
    var chat: GroupChat? = null
    var invites: ArrayList<GroupInvite>? = null
    var members_num: Int? = null
}