package it.uniparthenope.parthenopeddit.api.requests

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
    fun getUserGroupsTest1() {
        logUser(app.auth, "user1")

        groupsRequests.getUserGroups(
            {
                assertEquals(0, it.size)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception(it)
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun getUserGroupsTest2() {
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
    }

    @Test
    fun getInvitesToGroup() {
    }

    @Test
    fun getGroup() {
    }

    @Test
    fun inviteUsersToGroup() {
    }

    @Test
    fun getGroupInvites() {
    }

    @Test
    fun answerGroupInvite() {
    }

    @Test
    fun leaveGroup() {
    }

    @Test
    fun getGroupMembers() {
    }

    @Test
    fun makeMembersOwners() {
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