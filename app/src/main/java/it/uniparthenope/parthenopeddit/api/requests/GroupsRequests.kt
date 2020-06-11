package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.google.gson.Gson
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.*
import it.uniparthenope.parthenopeddit.util.TAG
import it.uniparthenope.parthenopeddit.util.toArrayList
import it.uniparthenope.parthenopeddit.util.toObject
import org.json.JSONArray

class GroupsRequests(private val ctx: Context, private val auth: AuthManager) {
    fun getUserGroups(
        onSuccess: (groupMemberships: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/"
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
                        onFail("Could not parse request result as group membership data.")
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

    fun createGroup(
        group_name: String,
        invitedUsersIds: List<String>,
        onGroupCreated: (createdGroup: Group) -> Unit,
        onGroupCreatedAndUsersInvited: (invitedUsers: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["group_name"] = group_name
                        params["invited_members"] = Gson().toJson(invitedUsersIds)
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->

                if( resultCode == 201 ) {
                    try {
                        onGroupCreated(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as group data")
                        Log.d(TAG, resultJson)
                        return@performRequest
                    }
                } else if( resultCode == 202 ) {
                    try {
                        onGroupCreatedAndUsersInvited(JSONArray(resultJson).toArrayList())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as group membership data")
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

    fun getUserInvitesToGroup(
        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/invites"
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
                        onFail("Could not parse request result as group invite data")
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

    fun getGroup(
        group_id: Int,
        onSuccess: (group: Group) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id"
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
                        onFail("Could not parse request result as group data")
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

    fun inviteUsersToGroup(
        group_id: Int,
        invitedUsersIds: List<String>,

        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/invite"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["users_list"] = Gson().toJson(invitedUsersIds)
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    try {
                        onSuccess(JSONArray(resultJson).toArrayList())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as group invite data")
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

    fun undoInvite(
        group_id: Int,
        user_id: String,
        onSuccess: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/invite/$user_id/undo"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    onSuccess()
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun getGroupInvites(
        group_id: Int,
        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/invite"
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
                        onFail("Could not parse request result as group invite data")
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

    fun searchInvitableUser(
        group_id: Int,
        searched_user_id: String,
        onSuccess: (users: ArrayList<User>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/invite/search/$searched_user_id"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            } , { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    onSuccess(JSONArray(resultJson).toArrayList())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun answerGroupInvite(
        group_id: Int,
        accept: Boolean,
        onDecline: () -> Unit,
        onAccept: (membership: GroupMember) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/invite/answer"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["answer"] = Gson().toJson(accept)
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    onDecline()
                } else if( resultCode == 202 ) {
                    try {
                        onAccept(resultJson.toObject())
                    } catch (e: Exception) {
                        onFail("Could not parse request result as group membership data")
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

    fun leaveGroup(
        group_id: Int,
        onSuccess: () -> Unit,
        onNewOwnerPromoted: () -> Unit,
        onGroupDisbanded: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/leave"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    onSuccess()
                } else if( resultCode == 202 ) {
                    onNewOwnerPromoted()
                } else if( resultCode == 203 ) {
                    onGroupDisbanded()
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun getGroupMembers(
        group_id: Int,
        onSuccess: (members: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/members"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)

            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    onSuccess(JSONArray(resultJson).toArrayList())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun removeFromGroup(
        group_id: Int,
        userId: String,
        onSuccess: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/kick/$userId"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    onSuccess()
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun makeMembersOwners(
        group_id: Int,
        newOwners: List<String>,
        onSuccess: (new_owners: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/members/make_owner"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["users_list"] = Gson().toJson(newOwners)
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)

            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    onSuccess(JSONArray(resultJson).toArrayList())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun getGroupPosts(
        group_id: Int,
        per_page: Int?,
        page: Int?,
        onSuccess: (posts: ArrayList<Post>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/posts/${
                        if(page == null) "" else {
                            if(per_page == null) "$page" else "$per_page/$page"
                        }
                    }"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)

            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 200 ) {
                    onSuccess(JSONArray(resultJson).toArrayList())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun publishPostToGroup(
        group_id: Int,
        title: String,
        body: String,
        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/groups/$group_id/posts"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["title"] = Gson().toJson(title)
                        params["body"] = Gson().toJson(body)
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)

            }, { resultCode: Int, resultJson: String ->
                if( resultCode == 201 ) {
                    onSuccess(resultJson.toObject())
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun getGroupMessages(
        group_id: Int,
        onSuccess: (messages: ArrayList<Message>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun sendMessageToGroup(
        group_id: Int,
        body: String,
        replies_to_message_id: Int,
        onSuccess: (message: Message) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}