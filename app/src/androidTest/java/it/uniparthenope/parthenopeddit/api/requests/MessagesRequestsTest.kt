package it.uniparthenope.parthenopeddit.api.requests

import it.uniparthenope.parthenopeddit.model.Comment
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.withLock

class MessagesRequestsTest : ApiRequestsTest() {

    lateinit var messagesRequests: MessagesRequests

    @Before
    override fun setUp() {
        super.setUp()
        messagesRequests = MessagesRequests(app, app.auth)
    }

    @After
    override fun tearDown() {
    }


    @Test
    fun getChats() {
        val username = "user3"
        logUser(app.auth, username)

        messagesRequests.getOpenChats(
            {
                assertEquals(1, it.size)
                assertEquals("user3", it[0].of_user_id)
                assertEquals("user1", it[0].other_user_chat!!.of_user_id)

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
}