package it.uniparthenope.parthenopeddit.api.namespaces

interface GroupsNamespace {
    fun getUserGroups()
    fun createGroup()

    fun getInvitesToGroup()

    fun getGroup()

    fun inviteUsersToGroup()
    fun getGroupInvites()

    fun answerGroupInvite()

    fun leaveGroup()

    fun getGroupMembers()
    fun makeMembersOwners()

    fun getGroupPosts()
    fun publishPostToGroup()

    fun getGroupMessages()
    fun sendMessageToGroup()
}