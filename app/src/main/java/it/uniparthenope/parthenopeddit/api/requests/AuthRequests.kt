package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import com.android.volley.Request
import it.uniparthenope.parthenopeddit.api.ApiClient
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.api.namespaces.AuthNamespace
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.toObject
import java.lang.Exception

class AuthRequests(private val ctx:Context, private val auth: AuthManager) : AuthNamespace {

    class Login(val token: String) : ApiRoute() {
        override val url: String
            get() = "$baseUrl/auth/login"
        override val httpMethod: Int
            get() = Request.Method.GET
        override val params: HashMap<String, String>
            get() = getParamsMap()
        override val headers: HashMap<String, String>
            get() = getHeadersMap(token)
    }

    override fun login(
        token:String,
        onLogin: (user: User) -> Unit,
        onFirstLogin: (user: User) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        ApiClient.getInstance(ctx).performRequest( Login(token),
            { resultCode: Int, resultJson: String ->
                if( resultCode == 200 || resultCode == 201 ) {
                    lateinit var user: User
                    try {
                        user = resultJson.toObject()
                    } catch (e: Exception) {
                        onFail(e.message?:"Could not parse login request result as user data")
                        return@performRequest
                    }
                    auth.login(token, user.id)
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
}