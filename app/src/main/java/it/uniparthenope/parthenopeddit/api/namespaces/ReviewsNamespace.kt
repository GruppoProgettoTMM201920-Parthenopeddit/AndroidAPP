package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.Review

interface ReviewsNamespace {
    fun publishNewReview(
        body:String,
        reviewed_course_id: Int,
        score_liking: Int,
        score_difficulty: Int,

        onSuccess: (review: Review) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getReview(
        reviewId:Int,

        onSuccess: (review: Review) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun getReviewWithComments(
        reviewId:Int,

        onSuccess: (review: Review) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun likeReview(
        reviewId:Int,

        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    )
    fun dislikeReview(
        reviewId:Int,

        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    )
}