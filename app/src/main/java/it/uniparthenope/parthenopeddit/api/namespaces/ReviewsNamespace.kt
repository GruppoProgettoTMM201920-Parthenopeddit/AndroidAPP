package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
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

        onLikePlaced: (score: LikeDislikeScore) -> Unit,
        onLikeRemoved: (score: LikeDislikeScore) -> Unit,
        onDislikeRemovedAndLikePlaced: (score: LikeDislikeScore) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun dislikeReview(
        reviewId:Int,

        onDislikePlaced: (score: LikeDislikeScore) -> Unit,
        onDislikeRemoved: (score: LikeDislikeScore) -> Unit,
        onLikeRemovedAndDislikePlaced: (score: LikeDislikeScore) -> Unit,
        onFail: (error: String) -> Unit
    )
}