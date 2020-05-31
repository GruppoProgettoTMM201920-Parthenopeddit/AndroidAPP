package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.User

interface GroupNamespace {

    /**
     * GET GROUP POST
     * Retrieves post of group given ID
     */
    fun getGroupPost(token: String, id_group: Int, completion: (postList: List<Post>?, error: String?) -> Unit)

    /**
     * GET GROUP INFO
     * Retrieves info of group given ID
     */
    fun getGroupInfo(token: String, id_group: Int, completion: (name: String?, num_members: Int?, created: String?, members: ArrayList<GroupMember>?, error: String?) -> Unit)
}