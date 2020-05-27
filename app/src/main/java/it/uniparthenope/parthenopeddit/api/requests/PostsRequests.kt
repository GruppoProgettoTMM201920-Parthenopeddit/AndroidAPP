package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.api.namespaces.PostsNamespace
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toObject

class PostsRequests(private val ctx: Context, private val auth: AuthManager) : PostsNamespace {

    class PublishNewPost(val token: String, val title: String, val body: String, val board_id: Int) : ApiRoute() {
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
            get() = getHeadersMap(token)
    }

    override fun publishNewPost(
        title: String,
        body: String,
        board_id: Int,
        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest( PublishNewPost(auth.token!!, title, body, board_id),
            { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        //onFail(e.message?:"Could not parse login request result as user data")
                        Log.d("debug", resultJson)
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
        TODO("Not yet implemented")
    }

    override fun getPostWithComments(
        postId: Int,
        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun likePost(
        postId: Int,
        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun dislikePost(
        postId: Int,
        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}