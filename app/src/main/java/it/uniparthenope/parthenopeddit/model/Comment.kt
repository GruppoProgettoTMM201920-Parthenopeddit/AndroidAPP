package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Comment(
    id: Int,
    body: String,
    timestamp: Date,
    author_id: String,

    @SerializedName("commented_content_id")
    val commented_content_id: Int

) : Content(id, body, timestamp, author_id), JSONConvertable {

    /* relationships */

    @SerializedName("commented_content")
    var commented_content: Content? = null
}
