package it.uniparthenope.parthenopeddit.api.requests

import it.uniparthenope.parthenopeddit.model.Comment
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class PostsRequestsTest : ApiRequestsTest() {

    lateinit var postsRequests: PostsRequests

    @Before
    override fun setUp() {
        super.setUp()
        postsRequests = PostsRequests(app, app.auth)
    }

    @After
    override fun tearDown() {
    }

    @Test
    fun publishNewPost() {
        val username = "user3"
        logUser(app.auth, username)

        val board_id = 0
        val title = "Title of test post"
        val body = "Body of test post"
        postsRequests.publishNewPost(
            title,
            body,
            board_id,
            {
                assertEquals(username, it.author!!.id)
                assertEquals(title, it.title)
                assertEquals(body, it.body)

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
    fun getPost() {
        val username = "user1"
        logUser(app.auth, username)

        val post_id = 1

        postsRequests.getPost(
            post_id,
            {
                assertEquals("post 1 di user1", it.title)
                assertEquals(8, it.comments_num)
                assertNull(it.comments)

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
    fun getPostWithComments() {
        val username = "user1"
        logUser(app.auth, username)

        val post_id = 1

        postsRequests.getPostWithComments(
            post_id,
            {
                assertEquals("post 1 di user1", it.title)
                assertEquals(8, it.comments_num)
                assertNotNull(it.comments)
                assertEquals(1, it.comments!!.size)
                val comment11 = it.comments!![0] as Comment
                assertEquals(11, comment11.id)

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
    fun likeAndDislikePost() {
        val username = "user1"
        logUser(app.auth, username)

        val post_id = 1

        postsRequests.likePost(
            post_id,
            {
                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }, {
                throw Exception()
            }, {
                throw Exception(it)
            }
        )

        lock.withLock {
            condition.await()
        }

        postsRequests.likePost(
            post_id,
            {
                throw Exception()
            }, {
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

        postsRequests.likePost(
            post_id,
            {
                lock.withLock {
                    condition.signal()
                }
            }, {
                throw Exception()
            }, {
                throw Exception()
            }, {
                throw Exception(it)
            }
        )

        lock.withLock {
            condition.await()
        }

        postsRequests.dislikePost(
            post_id,
            {
                throw Exception()
            }, {
                throw Exception()
            }, {
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

        postsRequests.dislikePost(
            post_id,
            {
                throw Exception()
            }, {
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
}