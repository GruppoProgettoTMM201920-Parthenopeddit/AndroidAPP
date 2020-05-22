package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Review

interface ReviewNamespace {
    /**
     * GET ALL REVIEW
     * Retrieves reviews of a course given ID
     */
    fun getAllReview(token:String, completion: (reviewList: List<Review>?, error: String?) -> Unit)
}