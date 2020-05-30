package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Comment(
    id: Int,
    body: String,
    timestamp: String,
    author_id: String,
    author: User? = null,

    val commented_content_id: Int,
    val root_content_id: Int

) : Content(id, body, timestamp, author_id, "comment"), JSONConvertable {
}
