package it.uniparthenope.parthenopeddit.api

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.nio.charset.Charset

class ApiRequest(
    private val route: ApiRoute,
    private val onSuccess: Response.Listener<ApiResponse>,
    onVolleyError:(volleyError: VolleyError)->Unit
) : Request<ApiResponse>(
    route.httpMethod,
    route.url,
    onVolleyError) {

    init {
        retryPolicy = DefaultRetryPolicy(route.timeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    }

    override fun getParams(): MutableMap<String, String> = route.params

    override fun getHeaders(): MutableMap<String, String> = route.headers

    override fun deliverResponse(response: ApiResponse) = onSuccess.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ApiResponse> {
        return try {
            val resultCode = response?.statusCode ?: 0
            val resultJson = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Response.success(
                ApiResponse(
                    resultCode,
                    resultJson
                ),
                HttpHeaderParser.parseCacheHeaders(response))
        } catch(e: Exception) {
            Response.error(ParseError(e))
        }
    }
}
