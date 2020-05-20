package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Post(
    id: Int,
    body: String? = null,
    timestamp: Date,
    author_id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("posted_to_board_id")
    val posted_to_board_id: Int? = null

) : Content(id, body, timestamp, author_id), JSONConvertable {

    /* relationships */

    @SerializedName("posted_to_board")
    var posted_to_board: Board? = null
}