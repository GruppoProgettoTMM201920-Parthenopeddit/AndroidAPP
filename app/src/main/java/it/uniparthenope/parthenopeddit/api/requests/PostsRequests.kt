package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.api.namespaces.PostsNamespace
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.TAG
import it.uniparthenope.parthenopeddit.util.toObject

class PostsRequests(private val ctx: Context, private val auth: AuthManager) : PostsNamespace {

    override fun publishNewPost(
        title: String,
        body: String,
        board_id: Int,
        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/posts/"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["title"] = title
                        params["body"] = body
                        params["board_id"] = board_id.toString()
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as post data")
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

    override fun getPost(
        postId: Int,
        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/posts/$postId"
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
                        onFail("Could not parse request result as post data")
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

    override fun getPostWithComments(
        postId: Int,
        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/posts/$postId/comments"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)

            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    Log.d(TAG, resultJson)
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as post data")
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

    override fun likePost(
        postId: Int,
        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/posts/$postId/like"
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

    override fun dislikePost(
        postId: Int,
        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/posts/$postId/dislike"
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