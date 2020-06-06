package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.toObject

class AuthRequests(private val ctx:Context, private val auth: AuthManager) {
    fun login(
        token:String,
        onLogin: (user: User) -> Unit,
        onFirstLogin: (user: User) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/auth/login"
                override val httpMethod: Int
                    get() = Request.Method.GET
                override val params: HashMap<String, String>
                    get() = getParamsMap()
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(token)
            }, {
                resultCode: Int, resultJson: String ->
                if( resultCode == 200 || resultCode == 201 ) {
                    lateinit var user: User
                    try {
                        user = resultJson.toObject()
                    } catch (e: Exception) {
                        onFail(e.message?:"Could not parse login request result as user data")
                        return@performRequest
                    }
                    if( resultCode == 200 ) {
                        onLogin(user)
                    } else {
                        onFirstLogin(user)
                    }
                } else {
                    onFail("Error : $resultCode")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }

    fun registerDeviceToken(
        deviceToken: String,
        onRegister: () -> Unit,
        onUpdate: () -> Unit,
        onFail: () -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest(
            object : ApiRoute() {
                override val url: String
                    get() = "$baseUrl/auth/register_device_token"
                override val httpMethod: Int
                    get() = Request.Method.POST
                override val params: HashMap<String, String>
                    get() {
                        val params = getParamsMap()
                        params["token"] = deviceToken
                        return params
                    }
                override val headers: HashMap<String, String>
                    get() = getHeadersMap(auth.token!!)
            }, { resultCode: Int, _ ->
                if( resultCode == 201 || resultCode == 200 ) {
                    onRegister()
                } else if (resultCode == 202 ) {
                    onUpdate()
                } else {
                    onFail()
                }
            }, { _, _ ->
                onFail()
            }
        )
    }
}