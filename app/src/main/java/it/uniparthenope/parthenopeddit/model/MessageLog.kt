package it.uniparthenope.parthenopeddit.model

class MessageLog(
    val id: Int,
    val user1_id: String,
    val user2_id: String,
    val messages : ArrayList<Message>? = null
)