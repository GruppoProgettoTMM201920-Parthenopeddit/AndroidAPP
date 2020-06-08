package it.uniparthenope.parthenopeddit.api.requests

import androidx.test.platform.app.InstrumentationRegistry
import it.uniparthenope.parthenopeddit.App
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User
import org.junit.After
import org.junit.Before
import java.util.concurrent.locks.ReentrantLock

open class ApiRequestsTest {

    /**
     * Tests assume working API and database populated with predefined mock data,
     * and login bypass enabled
     */

    lateinit var app: App

    @Before
    open fun setUp() {
        app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App
        app.auth.logout()
    }

    @After
    open fun tearDown() {
    }

    fun logUser(auth: AuthManager, username: String) {
        auth.login( auth.getToken(username, "123"), username, false )
    }

    fun getToken(auth: AuthManager, username: String) : String {
        return auth.getToken(username, "123")
    }

    val lock = ReentrantLock()
    val condition = lock.newCondition()
}