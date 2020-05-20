package it.uniparthenope.parthenopeddit.model
import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList

open class Content (
    @SerializedName("id")
    val id: Int,

    @SerializedName("body")
    val body: String? = null,

    @SerializedName("timestamp")
    val timestamp: Date,

    @SerializedName("author_id")
    val author_id: String

) : JSONConvertable {

    /* relationships */

    @SerializedName("author")
    var author: User? = null

    @SerializedName("comments")
    var comments: ArrayList<Comment>? = null

    /* aggregated data */

    @SerializedName("comments_num")
    var comments_num: Int? = null

    @SerializedName("likes_num")
    var likes_num: Int? = null

    @SerializedName("dislikes_num")
    var dislikes_num: Int? = null
}
