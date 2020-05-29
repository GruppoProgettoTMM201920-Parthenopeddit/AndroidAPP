package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.api.namespaces.CommentsNamespace
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.util.TAG
import it.uniparthenope.parthenopeddit.util.toObject

class CommentsRequests(private val ctx: Context, private val auth: AuthManager) : CommentsNamespace {

    override fun publishNewComment(
        body: String,
        commented_content_id: Int,
        onSuccess: (comment: Comment) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/comments/"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["body"] = body
                        params["commented_content_id"] = commented_content_id.toString()
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if (resultCode == 201) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as comment data")
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

    override fun getComment(
        commentId: Int,
        onSuccess: (comment: Comment) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/comments/$commentId"
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
                        onFail("Could not parse request result as comment data")
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

    override fun getCommentWithComments(
        commentId: Int,
        onSuccess: (comment: Comment) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/comments/$commentId/comments"
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
                        onFail("Could not parse request result as comment data")
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

    override fun likeComment(
        commentId: Int,
        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/comments/$commentId/like"
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

    override fun dislikeComment(
        commentId: Int,
        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/comments/$commentId/dislike"
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