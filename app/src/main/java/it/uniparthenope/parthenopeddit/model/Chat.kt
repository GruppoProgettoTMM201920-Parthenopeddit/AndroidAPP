package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Chat(
    val id: Int,
    val type: String
) : JSONConvertable {
    var received_messages: ArrayList<Message>? = null
}