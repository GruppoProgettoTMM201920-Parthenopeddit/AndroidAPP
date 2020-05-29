package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.api.namespaces.ReviewsNamespace
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Review
import it.uniparthenope.parthenopeddit.util.TAG
import it.uniparthenope.parthenopeddit.util.toObject

class ReviewsRequests(private val ctx: Context, private val auth: AuthManager) : ReviewsNamespace {

    override fun publishNewReview(
        body: String,
        reviewed_course_id: Int,
        score_liking: Int,
        score_difficulty: Int,
        onSuccess: (review: Review) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/reviews/"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["body"] = body
                        params["reviewed_course_id"] = reviewed_course_id.toString()
                        params["score_liking"] = score_liking.toString()
                        params["score_difficulty"] = score_difficulty.toString()
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if (resultCode == 201) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as review data")
                        Log.d(TAG, resultJson)
                        return@performRequest
                    }
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail(error)
            }
        )
    }

    override fun getReview(
        reviewId: Int,
        onSuccess: (review: Review) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/reviews/$reviewId"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as review data")
                        Log.d(TAG, resultJson)
                        return@performRequest
                    }
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    override fun getReviewWithComments(
        reviewId: Int,
        onSuccess: (review: Review) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/reviews/$reviewId/comments"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as review data")
                        Log.d(TAG, resultJson)
                        return@performRequest
                    }
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    override fun likeReview(
        reviewId: Int,
        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/reviews/$reviewId/like"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 210 ) {
                    onLikePlaced()
                } else if( resultCode == 211 ) {
                    onLikeRemoved()
                } else if( resultCode == 212 ) {
                    onDislikeRemovedAndLikePlaced()
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    override fun dislikeReview(
        reviewId: Int,
        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/reviews/$reviewId/dislike"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 210 ) {
                    onDislikePlaced()
                } else if( resultCode == 211 ) {
                    onDislikeRemoved()
                } else if( resultCode == 212 ) {
                    onLikeRemovedAndDislikePlaced()
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }
}