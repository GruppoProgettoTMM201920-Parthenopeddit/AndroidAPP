package it.uniparthenope.parthenopeddit.api.requests

import it.uniparthenope.parthenopeddit.model.Comment
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.withLock

class ReviewsRequestsTest : ApiRequestsTest() {

    lateinit var reviewsRequests: ReviewsRequests

    @Before
    override fun setUp() {
        super.setUp()
        reviewsRequests = ReviewsRequests(app, app.auth)
    }

    @After
    override fun tearDown() {
    }

    @Test
    fun publishNewReview() {
        val username = "user3"
        logUser(app.auth, username)

        val reviewed_course_id = 1 //course 1
        val body = "Body of test review"
        val liking_score = 4
        val difficulty_score = 2
        reviewsRequests.publishNewReview(
            body,
            reviewed_course_id,
            liking_score,
            difficulty_score,
            {
                assertEquals(username, it.author!!.id)
                assertEquals(body, it.body)
                assertEquals(1, it.reviewed_course_id)

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
    fun getReview() {
        val username = "user1"
        logUser(app.auth, username)

        val review_id = 3

        reviewsRequests.getReview(
            review_id,
            {
                assertEquals("Il miglior corso di sempre", it.body)

                assertEquals(1, it.reviewed_course_id)

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
    fun getReviewWithComments() {
        val username = "user1"
        logUser(app.auth, username)

        val review_id = 3

        reviewsRequests.getReviewWithComments(
            review_id,
            {
                assertEquals("Il miglior corso di sempre", it.body)

                assertEquals(1, it.reviewed_course_id)

                assertNotNull(it.comments)
                assertEquals(1, it.comments!!.size)
                val comment_r_1 = it.comments!![0] as Comment
                assertEquals(12, comment_r_1.id)

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
    fun likeAndDislikeReview() {
        val username = "user1"
        logUser(app.auth, username)

        val review_id = 3

        reviewsRequests.likeReview(
            review_id,
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

        reviewsRequests.likeReview(
            review_id,
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

        reviewsRequests.dislikeReview(
            review_id,
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

        reviewsRequests.dislikeReview(
            review_id,
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

        reviewsRequests.likeReview(
            review_id,
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
    }
}