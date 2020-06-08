package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.TAG
import it.uniparthenope.parthenopeddit.util.toArrayList
import it.uniparthenope.parthenopeddit.util.toObject
import org.json.JSONArray

class PostsRequests(private val ctx: Context, private val auth: AuthManager) {

    fun searchPost(
        searched_post_title: String,
        onSuccess: (users: ArrayList<Post>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/posts/search/$searched_post_title"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    try {
                        onSuccess(JSONArray(resultJson).toArrayList())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as posts data")
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

    fun publishNewPost(
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

    fun getPost(
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

    fun getPostWithComments(
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
                        Log.d(TAG, "TRYING TO PARSE")
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        Log.d(TAG, "ERROR IN PARSING")
                        Log.d(TAG, e.message?:"error")
                        e.printStackTrace()
                        onFail("Could not parse request result as post data")
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

    fun likePost(
        postId: Int,
        onLikePlaced: (score: LikeDislikeScore) -> Unit,
        onLikeRemoved: (score: LikeDislikeScore) -> Unit,
        onDislikeRemovedAndLikePlaced: (score: LikeDislikeScore) -> Unit,
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
                    onLikePlaced(resultJson.toObject())
                } else if( resultCode == 211 ) {
                    onLikeRemoved(resultJson.toObject())
                } else if( resultCode == 212 ) {
                    onDislikeRemovedAndLikePlaced(resultJson.toObject())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun dislikePost(
        postId: Int,
        onDislikePlaced: (score: LikeDislikeScore) -> Unit,
        onDislikeRemoved: (score: LikeDislikeScore) -> Unit,
        onLikeRemovedAndDislikePlaced: (score: LikeDislikeScore) -> Unit,
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
                    onDislikePlaced(resultJson.toObject())
                } else if( resultCode == 211 ) {
                    onDislikeRemoved(resultJson.toObject())
                } else if( resultCode == 212 ) {
                    onLikeRemovedAndDislikePlaced(resultJson.toObject())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }
}