package it.uniparthenope.parthenopeddit.model

class GroupMessageLog(
    val message: GroupMessage,
    val from_user: User?,
    val inviato: Boolean //TRUE inviato FALSE ricevuto
    //val letto: Boolean
)