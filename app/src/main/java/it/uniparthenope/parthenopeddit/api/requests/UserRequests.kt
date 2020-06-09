package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.Review
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.TAG
import it.uniparthenope.parthenopeddit.util.toArrayList
import it.uniparthenope.parthenopeddit.util.toObject
import org.json.JSONArray

class UserRequests(private val ctx: Context, private val auth: AuthManager) {

    fun searchUser(
        searched_user_id: String,
        onSuccess: (users: ArrayList<User>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/search/$searched_user_id"
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
                        onFail("Could not parse request result as user data")
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

    fun getUserByID(
        fetched_user_id: String,
        onSuccess: (user: User) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/$fetched_user_id"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get()  = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as user data")
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

    fun setDisplayName(
        name: String,
        onSuccess: (user: User) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/display_name"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["display_name"] = name
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    try {
                        onSuccess(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as user data")
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

    fun getUserFeed(
        page: Int = 1,
        perPage: Int = 20,
        onSuccess: (postList: ArrayList<Post>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/feed/$perPage/$page"
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

    fun getUserPublishedComments(
        user_id: String = auth.username!!,
        page: Int = 1,
        perPage: Int = 20,
        onSuccess: (postList: ArrayList<Comment>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/$user_id/published_comments/$perPage/$page"
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
                        onFail("Could not parse request result as comments data")
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

    fun getUserPublishedPosts(
        user_id: String = auth.username!!,
        page: Int = 1,
        perPage: Int = 20,
        onSuccess: (postList: ArrayList<Post>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/$user_id/published_posts/$perPage/$page"
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

    fun getUserPublishedReviews(
        user_id: String = auth.username!!,
        page: Int = 1,
        perPage: Int = 20,
        onSuccess: (postList: ArrayList<Review>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/user/$user_id/published_review/$perPage/$page"
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
                        onFail("Could not parse request result as reviews data")
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
}