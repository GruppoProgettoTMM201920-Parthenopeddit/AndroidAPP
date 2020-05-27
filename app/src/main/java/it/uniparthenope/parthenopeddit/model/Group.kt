package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Group(
    id: Int,
    name: String,

    @SerializedName("created_on")
    val created_on: Date
) : Board(id, name), JSONConvertable {

    @SerializedName("members")
    var members: ArrayList<User>? = null

    /*
    @SerializedName("chat")
    var chat: GroupChat? = null
     */

    @SerializedName("members_num")
    var members_num: Int? = null
}