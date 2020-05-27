package it.uniparthenope.parthenopeddit.api

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.Volley

class ApiClient constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: ApiClient? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiClient(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getVolleyErrorString(volleyError: VolleyError): String {
        return when (volleyError) {
            is TimeoutError -> "The conection timed out."
            is NoConnectionError -> "The conection couldn´t be established."
            is AuthFailureError -> "There was an authentication failure in your request."
            is ServerError -> "Server error while prosessing the server response."
            is NetworkError -> "Network error, please verify your conection."
            is ParseError -> "Error while prosessing the server response."
            else -> "Internet error"
        }
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    fun performRequest(
        route: ApiRoute,
        onRequestCompleted: (resultCode: Int, resultJson: String) -> Unit,
        onError: (volleyError: VolleyError, error: String) -> Unit
    ) {
        val request = ApiRequest(
            route,
            Response.Listener<ApiResponse> {response: ApiResponse ->
                onRequestCompleted.invoke( response.resultCode, response.resultJson )
            }
        )  { volleyError: VolleyError ->
            onError.invoke( volleyError, getVolleyErrorString(volleyError) )
        }
        addToRequestQueue(request)
    }
}