package it.uniparthenope.parthenopeddit.api.requests

import android.content.Context
import it.uniparthenope.parthenopeddit.api.APIRequestsQueue
import it.uniparthenope.parthenopeddit.api.ApiRoute
import it.uniparthenope.parthenopeddit.api.namespaces.AuthNamespace
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.toObject
import java.lang.Exception

class AuthRequests(private val ctx:Context) : AuthNamespace {
    override fun login(
        token:String,
        onLogin: (user: User) -> Unit,
        onFirstLogin: (user: User) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        APIRequestsQueue.getInstance(ctx).performRequest( ApiRoute.Login(token),
            { resultCode: Int, resultJson: String ->
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
                    onFail("Error : ${resultCode}")
                }
            }, { _, error: String ->
                onFail.invoke(error)
            }
        )
    }
}