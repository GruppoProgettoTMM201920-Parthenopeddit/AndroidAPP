package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.*

interface GroupsNamespace {
    fun getUserGroups(
        onSuccess: (groupMemberships: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun createGroup(
        group_name: String,
        invitedUsersIds: List<String>,
        onSuccess: (invitedUsers: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getInvitesToGroup(
        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getGroup(
        group_id: Int,

        onSuccess: (group: Group) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun inviteUsersToGroup(
        group_id: Int,
        invitedUsersIds: List<String>,

        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun getGroupInvites(
        group_id: Int,

        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun answerGroupInvite(
        group_id: Int,
        answer: Boolean,

        onSuccess: (membership: GroupMember) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun leaveGroup(
        group_id: Int,

        onSuccess: () -> Unit,
        onNewOwnerPromoted: () -> Unit,
        onGroupDisbanded: () -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getGroupMembers(
        group_id: Int,

        onSuccess: (members: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun makeMembersOwners(
        group_id: Int,
        invitedUsersIds: List<String>,

        onSuccess: (new_owners: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getGroupPosts(
        group_id: Int,

        onSuccess: (posts: ArrayList<Post>) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun publishPostToGroup(
        group_id: Int,
        title:String,
        body:String,

        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getGroupMessages(
        group_id: Int,

        onSuccess: (messages: ArrayList<Message>) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun sendMessageToGroup(
        group_id: Int,
        body: String,
        replies_to_message_id: Int,

        onSuccess: (message: Message) -> Unit,
        onFail: (error: String) -> Unit
    )
}