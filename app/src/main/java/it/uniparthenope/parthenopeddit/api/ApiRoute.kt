package it.uniparthenope.parthenopeddit.api

abstract class ApiRoute {
    val timeOut: Int
        get() {
            return 3000
        }

    val baseUrl: String
        get() {
            return "http://10.0.2.2:8000"
        }

    abstract val url: String
    abstract val httpMethod: Int
    abstract val params: HashMap<String, String>
    internal fun getParamsMap() : HashMap<String, String> {
        return hashMapOf()
    }
    abstract val headers: HashMap<String, String>
    internal fun getHeadersMap(token: String) : HashMap<String, String> {
        val headers: HashMap<String, String> = hashMapOf()
        headers["Accept"] = "application/json"
        headers["authorization"] = "Basic ${token}"
        return headers
    }
}