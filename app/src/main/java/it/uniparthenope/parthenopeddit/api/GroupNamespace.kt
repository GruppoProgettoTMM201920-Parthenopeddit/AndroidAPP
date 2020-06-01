package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.*

interface GroupNamespace {
    fun getUserGroups(
        onSuccess: (groupMemberships: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun createGroup(
        group_name: String,
        invitedUsersIds: List<String>,
        onSuccess: (invitedUsers: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getUserInvitesToGroup(
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
        accept: Boolean,
        onDecline: () -> Unit,
        onAccept: (membership: GroupMember) -> Unit,
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
        onSuccess: (newOwners: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun makeMembersOwners(
        group_id: Int,
        invitedUsersIds: List<String>,
        onSuccess: (new_owners: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    )
}