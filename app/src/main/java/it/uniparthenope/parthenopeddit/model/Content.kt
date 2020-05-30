package it.uniparthenope.parthenopeddit.model
import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList

open class Content (
    val id: Int,
    val body: String? = null,
    val timestamp: String,
    val author_id: String,
    val type: String,
    var author: User? = null,
    var comments: ArrayList<Comment>? = null,
    var comments_num: Int? = null,
    var likes_num: Int? = null,
    var dislikes_num: Int? = null
) : JSONConvertable
