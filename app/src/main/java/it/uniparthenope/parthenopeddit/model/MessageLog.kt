package it.uniparthenope.parthenopeddit.model

class MessageLog(
    val message: Message,
    val inviato: Boolean, //TRUE inviato FALSE ricevuto
    val letto: Boolean
)