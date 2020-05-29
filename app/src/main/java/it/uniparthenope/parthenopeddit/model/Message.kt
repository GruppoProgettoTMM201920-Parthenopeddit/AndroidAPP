package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Message (
    val id: Int,
    val body: String,
    val timestamp: String,
    val isRead: Boolean,
    val messageType: Int

) : JSONConvertable{
    companion object {
        const val TYPE_MY_MESSAGE = 0
        const val TYPE_FRIEND_MESSAGE = 1
    }
}