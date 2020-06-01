package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Course
import it.uniparthenope.parthenopeddit.model.Review

interface ReviewNamespace {
    /**
     * GET ALL REVIEW
     * Retrieves reviews of a course given ID
     */
    fun getAllReview(token:String, completion: (reviewList: List<Review>?, error: String?) -> Unit)

    /**
     * GET COURSE RATING
     * Retrieves info of a course given ID
     */
    fun getCourseInfo(token: String, courseId: Int, completion: (courseRating: Float, courseDifficulty: Float, numReviews: Int, courseName: String?, ifFollowed: Boolean,  error: String?) -> Unit)

    /**
     * GET COURSE REVIEWS
     * Retrieves rating and name of a course given ID
     */
    fun getCourseReviews(token: String, courseId: Int, completion: (reviewList: List<Review>?,  error: String?) -> Unit)
}