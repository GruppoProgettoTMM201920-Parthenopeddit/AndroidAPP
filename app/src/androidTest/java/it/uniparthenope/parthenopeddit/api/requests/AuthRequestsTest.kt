package it.uniparthenope.parthenopeddit.api.requests

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class AuthRequestsTest : ApiRequestsTest() {

    lateinit var authRequests: AuthRequests

    @Before
    override fun setUp() {
        super.setUp()
        authRequests = AuthRequests(app, app.auth)
    }

    @After
    override fun tearDown() {
    }

    @Test
    fun loginTest1() {
        authRequests.login(
            getToken(app.auth, "user1"),
            {
                assertEquals("user1", it.id)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }, {
                throw Exception(it)
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun loginTest2() {
        authRequests.login(
            getToken(app.auth, "user2"),
            {
                assertEquals("user2", it.id)

                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }, {
                throw Exception(it)
            }
        )

        lock.withLock {
            condition.await()
        }
    }

    @Test
    fun loginTest3() {
        authRequests.login(
            getToken(app.auth, "user3_new"),
            {
                throw Exception()
            }, {
                assertEquals("user3_new", it.id)

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
    fun registerDeviceToken() {
        // TODO test device registration
    }
}