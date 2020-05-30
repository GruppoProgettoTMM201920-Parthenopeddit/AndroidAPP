package it.uniparthenope.parthenopeddit.api.requests

import it.uniparthenope.parthenopeddit.model.Comment
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.withLock

class ReviewsRequestsTest : ApiRequestsTest() {

    lateinit var commentsRequests: CommentsRequests

    @Before
    override fun setUp() {
        super.setUp()
        commentsRequests = CommentsRequests(app, app.auth)
    }

    @After
    override fun tearDown() {
    }

    @Test
    fun publishNewComment() {
        val username = "user3"
        logUser(app.auth, username)

        val commented_content_id = 1 //post1
        val body = "Body of test comment"
        commentsRequests.publishNewComment(
            body,
            commented_content_id,
            {
                assertEquals(username, it.author!!.id)
                assertEquals(body, it.body)
                assertEquals(1, it.commented_content_id)

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
    fun getComment() {
        val username = "user1"
        logUser(app.auth, username)

        val comment_id = 11

        commentsRequests.getComment(
            comment_id,
            {
                assertEquals("Test commento top level a post 1", it.body)

                assertEquals(1, it.commented_content_id)

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
    fun getCommentWithComments() {
        val username = "user1"
        logUser(app.auth, username)

        val comment_id = 11

        commentsRequests.getCommentWithComments(
            comment_id,
            {
                assertEquals("Test commento top level a post 1", it.body)

                assertEquals(1, it.commented_content_id)

                assertNotNull(it.comments)
                assertEquals(1, it.comments!!.size)
                val comment12 = it.comments!![0] as Comment
                assertEquals(12, comment12.id)

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
    fun likeAndDislikeComment() {
        val username = "user1"
        logUser(app.auth, username)

        val comment_id = 11
        /**
         * assuming comment 11 already has like as in mockdata
         */

        commentsRequests.likeComment(
            comment_id,
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

        commentsRequests.likeComment(
            comment_id,
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

        commentsRequests.dislikeComment(
            comment_id,
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

        commentsRequests.dislikeComment(
            comment_id,
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