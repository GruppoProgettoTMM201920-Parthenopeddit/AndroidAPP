package it.uniparthenope.parthenopeddit.api.requests

import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupInvite
import it.uniparthenope.parthenopeddit.model.GroupMember
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.concurrent.withLock

class GroupsRequestsTest : ApiRequestsTest() {

    lateinit var groupsRequests: GroupsRequests

    @Before
    override fun setUp() {
        super.setUp()
        groupsRequests = GroupsRequests(app, app.auth)
    }

    @After
    override fun tearDown() {
    }

    @Test
    fun getUserGroupsTest() {
        logUser(app.auth, "user2")

        groupsRequests.getUserGroups(
            {
                assertEquals(1, it.size)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun createGroup() {
        logUser(app.auth, "user1")

        groupsRequests.createGroup(
            "TEST GROUP",
            listOf("user2", "user3"),
            { invitedUsers: ArrayList<GroupInvite> ->
                assertEquals(2, invitedUsers.size)
                assertEquals("TEST GROUP", invitedUsers[0].group!!.name)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getInvitesToGroup() {
        logUser(app.auth, "user1")

        groupsRequests.getUserInvitesToGroup(
            { groupInvites: ArrayList<GroupInvite> ->
                assertEquals(1, groupInvites.size)
                assertEquals("user2", groupInvites[0].inviter_id)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getGroup1() {
        logUser(app.auth, "user1")

        val group_id = 2

        groupsRequests.getGroup(
            group_id,
            { group: Group ->
                throw Exception()
            }, {
                lock.withLock {
                    condition.signal()
                }
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getGroup2() {
        logUser(app.auth, "user2")

        val group_id = 2

        groupsRequests.getGroup(
            group_id,
            { group: Group ->
                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun inviteUsersToGroup() {
        logUser(app.auth, "user3")

        val group_id = 3

        groupsRequests.inviteUsersToGroup(
            group_id,
            listOf("user2"),
            { groupInvites: ArrayList<GroupInvite> ->
                assertEquals(1, groupInvites.size)
                assertEquals("user2", groupInvites[0].invited_id)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getGroupInvites() {
        logUser(app.auth, "user2")

        val group_id = 2

        groupsRequests.getGroupInvites(
            group_id,
            { groupInvites: ArrayList<GroupInvite> ->
                assertEquals(1, groupInvites.size)
                assertEquals("user1", groupInvites[0].invited_id)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun answerGroupInvite() {
        logUser(app.auth, "user1")

        val group_id = 2

        groupsRequests.answerGroupInvite(
            group_id,
            true,
            {
                throw Exception()
            },
            { membership: GroupMember ->
                assertNotNull(membership)
                assertEquals( group_id, membership.group_id )
                assertEquals( "user1", membership.user_id )
                assertEquals( false, membership.is_owner )

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun leaveGroup1() {
        logUser(app.auth, "user3")

        val group_id = 3

        groupsRequests.leaveGroup(
            group_id,
            {
                throw Exception()
            }, {
                throw Exception()
            }, {
                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun leaveGroup2() {
        logUser(app.auth, "user2")

        val group_id = 2

        groupsRequests.leaveGroup(
            group_id,
            {
                throw Exception()
            }, {
                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getGroupMembers() {
        logUser(app.auth, "user2")

        val group_id = 2

        groupsRequests.getGroupMembers(
            group_id,
            { members: ArrayList<GroupMember> ->
                assertNotNull(members)
                assertEquals(2, members.size)
                assertEquals("user2", members[0].user_id)
                assertEquals(true, members[0].is_owner)
                assertEquals("user3", members[1].user_id)
                assertEquals(false, members[1].is_owner)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun makeMembersOwners() {
        logUser(app.auth, "user2")

        val group_id = 2

        groupsRequests.makeMembersOwners(
            group_id,
            listOf("user3"),
            { newOwners: ArrayList<GroupMember> ->
                assertNotNull(newOwners)
                assertEquals(1, newOwners.size)
                assertEquals("user3", newOwners[0].user_id)
                assertEquals(true, newOwners[0].is_owner)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getGroupPosts() {
    }

    @Test
    fun publishPostToGroup() {
    }

    @Test
    fun getGroupMessages() {
    }

    @Test
    fun sendMessageToGroup() {
    }
}