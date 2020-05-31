package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.UsersChat

interface ChatNamespace {
    /**
     * GET CHAT
     * Retrieves user's chat of given ID
     */
    fun getChat(token: String, userId:Int, completion: (chat: ArrayList<UsersChat>?, error: String?) -> Unit)
}