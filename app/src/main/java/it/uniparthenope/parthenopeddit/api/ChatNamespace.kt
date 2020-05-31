package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Message
import it.uniparthenope.parthenopeddit.model.MessageLog
import it.uniparthenope.parthenopeddit.model.UsersChat

interface ChatNamespace {
    /**
     * GET CHAT
     * Retrieves user's chat of given ID
     */
    fun getChat(token: String, userId:Int, completion: (chat: ArrayList<UsersChat>?, error: String?) -> Unit)

    /**
     * GET CHAT MESSAGES
     * Retrieves chat messages of given user ID
     */
    fun getChatMessages(token: String, user1Id: String, user2Id: String, completion: (chat: ArrayList<Message>?, error: String?) -> Unit)
}